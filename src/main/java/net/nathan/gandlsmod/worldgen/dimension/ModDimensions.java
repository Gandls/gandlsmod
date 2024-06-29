package net.nathan.gandlsmod.worldgen.dimension;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPresets;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.nathan.gandlsmod.GandlsMod;
import net.nathan.gandlsmod.worldgen.biome.ModBiomes;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;

public class ModDimensions {
    public static final ResourceKey<LevelStem> GANDLSDIM_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            new ResourceLocation(GandlsMod.MOD_ID, "gandlsdim"));
    public static final ResourceKey<Level> GANDLSDIM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(GandlsMod.MOD_ID, "gandlsdim"));
    public static final ResourceKey<DimensionType> GANDLS_DIM_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE,
            new ResourceLocation(GandlsMod.MOD_ID, "gandlsdim_type"));


    public static void bootstrapType(BootstapContext<DimensionType> context) {
        context.register(GANDLS_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000), // fixedTime
                false, // hasSkylight
                false, // hasCeiling
                false, // ultraWarm
                false, // natural
                1.0, // coordinateScale
                true, // bedWorks
                false, // respawnAnchorWorks
                0, // minY
                16, // height
                16, // logicalHeight
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                BuiltinDimensionTypes.END_EFFECTS, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)));
    }

    public static void bootstrapStem(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomeRegistry = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseGenSettings = context.lookup(Registries.NOISE_SETTINGS);

        HolderGetter<StructureSet> holdergetter = context.lookup(Registries.STRUCTURE_SET);
        HolderGetter<PlacedFeature> holdergetter1 = context.lookup(Registries.PLACED_FEATURE);
        HolderSet.Direct<StructureSet> direct = HolderSet.direct(ImmutableSet.of(BuiltinStructureSets.VILLAGES).stream().map(holdergetter::getOrThrow).collect(Collectors.toList()));

        FlatLevelGeneratorSettings flatlevelgeneratorsettings = new FlatLevelGeneratorSettings(Optional.of(direct), biomeRegistry.getOrThrow(Biomes.THE_END), FlatLevelGeneratorSettings.createLakesList(holdergetter1));
        FlatLayerInfo barriers = new FlatLayerInfo(14, Blocks.BARRIER);
        FlatLayerInfo gateways = new FlatLayerInfo(1, Blocks.END_PORTAL);
        FlatLayerInfo barrier = new FlatLayerInfo(1, Blocks.BARRIER);
        flatlevelgeneratorsettings.getLayersInfo().add(barriers);
        flatlevelgeneratorsettings.getLayersInfo().add(gateways);
        flatlevelgeneratorsettings.getLayersInfo().add(barrier);

        //FlatLevelGeneratorSettings x = context.lookup(Registries.FLAT_LEVEL_GENERATOR_PRESET).getOrThrow(FlatLevelGeneratorPresets.BOTTOMLESS_PIT).get().settings();

        FlatLevelSource flatChunkGenerator = new FlatLevelSource(
                flatlevelgeneratorsettings
        );

        NoiseBasedChunkGenerator wrappedChunkGenerator = new NoiseBasedChunkGenerator(
                new FixedBiomeSource(biomeRegistry.getOrThrow(Biomes.BIRCH_FOREST)),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.END));

        NoiseBasedChunkGenerator noiseBasedChunkGenerator = new NoiseBasedChunkGenerator(
                MultiNoiseBiomeSource.createFromList(
                        new Climate.ParameterList<>(List.of(Pair.of(
                                        Climate.parameters(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), biomeRegistry.getOrThrow(ModBiomes.TEST_BIOME))
                        ))),
                noiseGenSettings.getOrThrow(NoiseGeneratorSettings.AMPLIFIED));

        //Any attempt at flatLevelSources throws errors in runData and doesn't change the json
        LevelStem stem = new LevelStem(dimTypes.getOrThrow(ModDimensions.GANDLS_DIM_TYPE), flatChunkGenerator);

        context.register(GANDLSDIM_KEY , stem);
    }



}
