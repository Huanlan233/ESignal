package htext.top.esignal.common.block

import htext.top.esignal.ESignal
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Material
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object Blocks {
	val CIRCUIT_BLOCK = BlockCircuit(FabricBlockSettings.of(Material.STONE))

	fun registry(){
		Registry.register(Registry.BLOCK, Identifier(ESignal.MOD_ID, "circuit"), CIRCUIT_BLOCK)
	}
}