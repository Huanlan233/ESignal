package top.htext.esignal.common.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.WorldAccess

class SignalBlock(settings: Settings?) : Block(settings), CircuitConnectableBlock, CircuitChargeableBlock {
	override fun updateConnection(pos: BlockPos, state: BlockState, access: WorldAccess) {

	}
	override fun emitSignal(pos: BlockPos, state: BlockState, access: WorldAccess): Boolean {
		return true
	}
	override fun receiveSignal(pos: BlockPos, state: BlockState, access: WorldAccess) {

	}
}