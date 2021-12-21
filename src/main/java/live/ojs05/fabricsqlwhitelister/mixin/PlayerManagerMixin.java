package live.ojs05.fabricsqlwhitelister.mixin;

import com.mojang.authlib.GameProfile;
import live.ojs05.fabricsqlwhitelister.DatabaseManager;
import net.minecraft.server.PlayerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Redirect(method = "checkCanJoin", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;isWhitelisted(Lcom/mojang/authlib/GameProfile;)Z"))
    public boolean isWhitelisted(PlayerManager instance, GameProfile profile) {
        return DatabaseManager.checkIfWhitelisted(profile);
    }

}
