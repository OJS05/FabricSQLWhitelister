package live.ojs05.fabricsqlwhitelister.mixin;

import com.mojang.brigadier.CommandDispatcher;
import live.ojs05.fabricsqlwhitelister.WhitelistCommand;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CommandManager.class)
public class CommandManagerMixin {
    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/dedicated/command/WhitelistCommand;register(Lcom/mojang/brigadier/CommandDispatcher;)V"))
    public void onRegister(CommandDispatcher<ServerCommandSource> dispatcher) {
        WhitelistCommand.register(dispatcher);
    }

}
