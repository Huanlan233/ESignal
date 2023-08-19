package top.htext.esignal.common.init

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Material
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import top.htext.esignal.ESignal
import top.htext.esignal.common.block.CircuitBlock

object Blocks {
	val CIRCUIT_BLOCK = CircuitBlock(FabricBlockSettings.of(Material.STONE))

	fun registry(){
		Registry.register(Registry.BLOCK, Identifier(ESignal.MOD_ID, "circuit"), CIRCUIT_BLOCK)
	}
}