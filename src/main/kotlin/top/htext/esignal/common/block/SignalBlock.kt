package top.htext.esignal.common.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.enums.WireConnection
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.WorldAccess
import top.htext.esignal.common.block.interfaces.SignalChargeableBlock
import top.htext.esignal.common.block.interfaces.SignalConnectableBlock

class SignalBlock(settings: Settings?) : Block(settings), SignalConnectableBlock, SignalChargeableBlock {
	override fun updateConnection(pos: BlockPos, state: BlockState, direction: Direction, access: WorldAccess): WireConnection {
		return WireConnection.NONE
	}
	override fun isEmittedSignal(pos: BlockPos, state: BlockState, access: WorldAccess): Boolean {
		return true
	}
	override fun isReceivedSignal(pos: BlockPos, state: BlockState, direction: Direction, access: WorldAccess): Boolean {
		return false
	}
}