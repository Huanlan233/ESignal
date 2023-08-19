package top.htext.esignal;

import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import top.htext.esignal.common.init.Blocks
import top.htext.esignal.common.init.Items

class ESignal: ModInitializer {
	companion object {
		const val MOD_ID: String = "esignal"
		const val MOD_NAME: String = "ESignal"
		val LOGGER: Logger = LogManager.getLogger(MOD_NAME)
	}
	override fun onInitialize() {
		LOGGER.info("Initialized.")
		LOGGER.debug("Registers items and blocks")
		Items.registry()
		Blocks.registry()
	}
}
