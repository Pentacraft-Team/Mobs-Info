package com.kuba6000.mobsinfo.mixin;

import static com.kuba6000.mobsinfo.mixin.TargetedMod.BATTLE_GEAR_2;
import static com.kuba6000.mobsinfo.mixin.TargetedMod.CHOCO_CRAFT;
import static com.kuba6000.mobsinfo.mixin.TargetedMod.DQ_RESPECT;
import static com.kuba6000.mobsinfo.mixin.TargetedMod.DRACONIC_EVOLUTION;
import static com.kuba6000.mobsinfo.mixin.TargetedMod.ENDER_IO;
import static com.kuba6000.mobsinfo.mixin.TargetedMod.INFERNAL_MOBS;
import static com.kuba6000.mobsinfo.mixin.TargetedMod.VANILLA;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import cpw.mods.fml.relauncher.FMLLaunchHandler;

public enum Mixin {

    // Minecraft
    EnchantmentHelperMixin("minecraft.EnchantmentHelperMixin", VANILLA),
    EntityAccessor("minecraft.EntityAccessor", VANILLA),
    EntityLivingAccessor("minecraft.EntityLivingAccessor", VANILLA),
    EntityLivingBaseAccessor("minecraft.EntityLivingBaseAccessor", VANILLA),
    EntitySlimeAccessor("minecraft.EntitySlimeAccessor", VANILLA),
    RendererLivingEntityAccessor("minecraft.RendererLivingEntityAccessor", VANILLA),

    // Infernal Mobs
    InfernalMobsCoreAccessor("InfernalMobs.InfernalMobsCoreAccessor", INFERNAL_MOBS),

    // Ender IO
    ItemSoulVesselAccessor("EnderIO.ItemSoulVesselAccessor", ENDER_IO),
    BlockPoweredSpawnerAccessor("EnderIO.BlockPoweredSpawnerAccessor", ENDER_IO),

    // Draconic Evolution
    MinecraftForgeEventHandlerAccessor("DraconicEvolution.MinecraftForgeEventHandlerAccessor", DRACONIC_EVOLUTION),
    RenderMobSoulMixin("DraconicEvolution.RenderMobSoulMixin", DRACONIC_EVOLUTION, BATTLE_GEAR_2),

    // DQRespect
    FuncCalcMobParamMixin("DQRespect.FuncCalcMobParamMixin", DQ_RESPECT),
    DqmEntitySweetbagMixin("DQRespect.DqmEntitySweetbagMixin", DQ_RESPECT),

    // ChocoCraft
    EntityAnimalChocoboMixin("ChocoCraft.EntityAnimalChocoboMixin", CHOCO_CRAFT),

    ;

    public final String mixinClass;
    public final List<TargetedMod> targetedMods;
    private final Side side;

    Mixin(String mixinClass, Side side, TargetedMod... targetedMods) {
        this.mixinClass = mixinClass;
        this.targetedMods = Arrays.asList(targetedMods);
        this.side = side;
    }

    Mixin(String mixinClass, TargetedMod... targetedMods) {
        this.mixinClass = mixinClass;
        this.targetedMods = Arrays.asList(targetedMods);
        this.side = Side.BOTH;
    }

    public boolean shouldLoad(List<TargetedMod> loadedMods) {
        return (side == Side.BOTH || side == Side.SERVER && FMLLaunchHandler.side()
            .isServer()
            || side == Side.CLIENT && FMLLaunchHandler.side()
                .isClient())
            && new HashSet<>(loadedMods).containsAll(targetedMods);
    }

    enum Side {
        BOTH,
        CLIENT,
        SERVER
    }
}
