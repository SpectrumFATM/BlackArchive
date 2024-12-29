package net.SpectrumFATM.black_archive.entity.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import net.SpectrumFATM.BlackArchive;
import net.SpectrumFATM.black_archive.config.BlackArchiveConfig;
import net.SpectrumFATM.black_archive.entity.custom.DalekPuppetEntity;
import net.SpectrumFATM.black_archive.entity.features.DalekEyestalkFeatureRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DalekPuppetRenderer extends LivingEntityRenderer<DalekPuppetEntity, DalekPuppetModel<DalekPuppetEntity>> {

    private final Executor skinDownloadExecutor = Executors.newCachedThreadPool();
    private UUID playerUUID;

    public DalekPuppetRenderer(EntityRendererProvider.Context context) {
        super(context, new DalekPuppetModel<>(context.bakeLayer(ModModelLayers.DALEK_SLAVE)), 0.5f);
        this.addLayer(new DalekEyestalkFeatureRenderer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(DalekPuppetEntity entity) {
        this.playerUUID = entity.getPlayerUUID();

        if (playerUUID == null) {
            // UUID is not available, return fallback skin and retry later
            BlackArchive.LOGGER.warn("playerUUID is null, returning fallback skin.");
            return getFallbackSkin(entity);
        }

        // Proceed with normal skin retrieval if UUID is valid
        if (!BlackArchiveConfig.CLIENT.shouldCacheSkins.get()) {
            return getFallbackSkin(entity);
        }

        ResourceLocation cachedSkin = getCachedSkin(playerUUID);

        if (cachedSkin != null) {
            return cachedSkin;
        }

        CompletableFuture.runAsync(() -> downloadAndCacheSkin(playerUUID, entity), skinDownloadExecutor);

        return getFallbackSkin(entity);
    }

    private void downloadAndCacheSkin(UUID playerUUID, DalekPuppetEntity entity) {
        String skinUrl = fetchSkinUrlFromUUID(playerUUID);
        if (skinUrl == null || skinUrl.isEmpty()) {
            return; // Avoid proceeding if no valid skin URL was found
        }

        Path cacheDir = Paths.get("config", "black_archive", "skins");
        try {
            Files.createDirectories(cacheDir);

            Path skinPath = cacheDir.resolve(playerUUID.toString() + ".png");

            try (InputStream in = new URL(skinUrl).openStream()) {
                Files.copy(in, skinPath);

                // After downloading, register the texture on the main thread
                Minecraft.getInstance().execute(() -> loadTextureFromFile(skinPath.toString(), playerUUID));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String fetchSkinUrlFromUUID(UUID playerUUID) {
        String urlString = "https://sessionserver.mojang.com/session/minecraft/profile/" + playerUUID.toString();
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (connection.getResponseCode() == 200) {
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                JsonArray properties = json.getAsJsonArray("properties");

                for (int i = 0; i < properties.size(); i++) {
                    JsonObject property = properties.get(i).getAsJsonObject();
                    if (property.get("name").getAsString().equals("textures")) {
                        String encodedData = property.get("value").getAsString();
                        String decodedData = new String(Base64.decodeBase64(encodedData));
                        JsonObject decodedJson = JsonParser.parseString(decodedData).getAsJsonObject();
                        JsonObject textures = decodedJson.getAsJsonObject("textures");
                        JsonObject skin = textures.getAsJsonObject("SKIN");

                        return skin.get("url").getAsString();
                    }
                }
            } else {
                BlackArchive.LOGGER.warn("Failed to fetch skin data for player UUID: {}", playerUUID.toString());
            }
        } catch (Exception e) {
            BlackArchive.LOGGER.error("Error while fetching skin data: {}", e.getMessage());
        }
        return null;
    }

    private ResourceLocation getCachedSkin(UUID playerUUID) {
        Path cacheDir = Paths.get("config", "black_archive", "skins");
        Path skinPath = cacheDir.resolve(playerUUID.toString() + ".png");

        if (Files.exists(skinPath)) {
            return loadTextureFromFile(skinPath.toString(), playerUUID);
        }

        return null;
    }

    private ResourceLocation loadTextureFromFile(String filePath, UUID playerUUID) {
        try {
            NativeImage image = NativeImage.read(Files.newInputStream(Paths.get(filePath)));
            if (image == null) {
                return null;
            }

            DynamicTexture nativeImageBackedTexture = new DynamicTexture(image);
            ResourceLocation textureId = new ResourceLocation("black_archive", "skins/" + playerUUID.toString());
            Minecraft.getInstance().getTextureManager().register(textureId, nativeImageBackedTexture);

            return textureId;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ResourceLocation getFallbackSkin(DalekPuppetEntity entity) {
        switch (entity.getSkinId()) {
            case 0:
                return new ResourceLocation("minecraft", "textures/entity/player/wide/alex.png");
            case 1:
                return new ResourceLocation("minecraft", "textures/entity/player/wide/ari.png");
            case 2:
                return new ResourceLocation("minecraft", "textures/entity/player/wide/efe.png");
            case 3:
                return new ResourceLocation("minecraft", "textures/entity/player/wide/kai.png");
            case 4:
                return new ResourceLocation("minecraft", "textures/entity/player/wide/makena.png");
            case 5:
                return new ResourceLocation("minecraft", "textures/entity/player/wide/noor.png");
            case 6:
                return new ResourceLocation("minecraft", "textures/entity/player/wide/sunny.png");
            case 7:
                return new ResourceLocation("minecraft", "textures/entity/player/wide/zuri.png");
            default:
                return new ResourceLocation("minecraft", "textures/entity/player/wide/steve.png");
        }
    }

    @Override
    protected void renderNameTag(DalekPuppetEntity entity, Component text, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        if (entity.shouldShowName()) {
            super.renderNameTag(entity, text, matrices, vertexConsumers, light);
        }
    }
}
