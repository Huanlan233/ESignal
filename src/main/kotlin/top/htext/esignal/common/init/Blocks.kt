package top.htext.esignal.common.init

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Blocks
import net.minecraft.block.MapColor
import net.minecraft.block.Material
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import top.htext.esignal.ESignal
import top.htext.esignal.common.block.CircuitBlock
import top.htext.esignal.common.block.SignalBlock

object Blocks {
	val CIRCUIT_BLOCK = CircuitBlock(FabricBlockSettings.of(Material.STONE).noCollision())
	val SIGNAL_BLOCK = SignalBlock(FabricBlockSettings.of(Material.METAL, MapColor.BLUE).requiresTool().strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL))

	fun registry(){
		Registry.register(Registry.BLOCK, Identifier(ESignal.MOD_ID, "circuit"), CIRCUIT_BLOCK)
		Registry.register(Registry.BLOCK, Identifier(ESignal.MOD_ID, "signal_block"), SIGNAL_BLOCK)
	}
}