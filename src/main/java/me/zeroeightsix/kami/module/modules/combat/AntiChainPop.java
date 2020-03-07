package me.zeroeightsix.kami.module.modules.combat;

import net.minecraft.init.Items;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;
import me.zeroeightsix.kami.setting.Settings;
import me.zeroeightsix.kami.setting.Setting;

@Module.Info(name = "AntiChainPop", description = "Enables surround when you pop a set number of totems", category = Module.Category.COMBAT)

public class AntiChainPop extends Module {
	private Setting<Double> enablePops = register(Settings.d("Enable Pops", 1.0));
	private Setting<DebugMsgs> debugMsgs = register(Settings.e("Debug Messages",DebugMsgs.NONE));
	
	private enum DebugMsgs{ 
		NONE,
		ALL,
	};
	
	private int totemsPopped = 0;
	
	@Override
	public void onUpdate() {
		if (mc.player.getHealth() <= 0.1 && ModuleManager.getModuleByName("AutoTotem").isEnabled()) {
			totemsPopped++;
			if (debugMsgs.getValue() == DebugMsgs.ALL) Command.sendChatMessage("&4[&cALERT&4]&r You just popped a totem!");
		}
		if (totemsPopped >= enablePops.getValue()) {
			ModuleManager.getModuleByName("Surround").enable();
			totemsPopped = 0;
		}
	}
	
	@Override
	public void onEnable() {
		totemsPopped = 0;
	}
	
}
