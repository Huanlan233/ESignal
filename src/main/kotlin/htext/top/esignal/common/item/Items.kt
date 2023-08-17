package htext.top.esignal.common.item

import htext.top.esignal.ESignal
import htext.top.esignal.common.block.Blocks
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object Items {
	val CIRCUIT_ITEM = ItemCircuit(Blocks.CIRCUIT_BLOCK, FabricItemSettings().group(ItemGroup.REDSTONE))
	fun registry(){
		Registry.register(Registry.ITEM, Identifier(ESignal.MOD_ID, "circuit"), CIRCUIT_ITEM)
	}
}