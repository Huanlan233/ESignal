package top.htext.esignal.common.init

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import top.htext.esignal.ESignal

object Items {
	val CIRCUIT_ITEM = BlockItem(Blocks.CIRCUIT_BLOCK, FabricItemSettings().group(ItemGroup.REDSTONE))
	val SIGNAL_ITEM = BlockItem(Blocks.SIGNAL_BLOCK, FabricItemSettings().group(ItemGroup.REDSTONE))
	fun registry(){
		Registry.register(Registry.ITEM, Identifier(ESignal.MOD_ID, "circuit"), CIRCUIT_ITEM)
		Registry.register(Registry.ITEM, Identifier(ESignal.MOD_ID, "signal_block"), SIGNAL_ITEM)
	}
}