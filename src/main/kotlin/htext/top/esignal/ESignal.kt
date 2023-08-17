package htext.top.esignal;

import htext.top.esignal.common.block.Blocks
import htext.top.esignal.common.item.Items
import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager

class ESignal: ModInitializer {
	companion object {
		val MOD_ID: String = "esignal"
		val MOD_NAME: String = "ESignal"
		val LOGGER = LogManager.getLogger(MOD_NAME)
	}
	override fun onInitialize() {
		LOGGER.info("Initialized.")
		LOGGER.debug("Registers items and blocks")
		Items.registry()
		Blocks.registry()
	}
}
