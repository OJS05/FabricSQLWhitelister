package live.ojs05.fabricsqlwhitelister;

import me.lucko.fabric.api.permissions.v0.Permissions;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import static net.minecraft.server.command.CommandManager.literal;

public class WhitelistCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){

        CommandRegistrationCallback.EVENT.register( (dispatcher1, dedicated) -> dispatcher.register(literal("whitelist")
                .requires(Permissions.require("fabricsqlwhitelister.main"))
                .executes(ctx -> {
                    ctx.getSource().getPlayer().sendMessage(new LiteralText("Welcome to FabricSQLWhitelister!"), MessageType.CHAT, ctx.getSource().getPlayer().getUuid());
                    return 1;
                })
                .then(literal("add").then(CommandManager.argument("username", GameProfileArgumentType.gameProfile())
                        .requires(Permissions.require("fabricsqlwhitelister.add"))
                        .executes(ctx -> {
                            ctx.getSource().sendFeedback(new LiteralText("Added " + GameProfileArgumentType.getProfileArgument(ctx, "username").stream().findFirst().get().getName() + " to the whitelist!"), true);
                            DatabaseManager.addPlayer(GameProfileArgumentType.getProfileArgument(ctx, "username").stream().findFirst().get());
                            return 1;
                        })
                ))
                .then(literal("remove").then(CommandManager.argument("username", GameProfileArgumentType.gameProfile())
                        .requires(Permissions.require("fabricsqlwhitelister.remove"))
                        .executes(ctx -> {
                            ctx.getSource().sendFeedback(new LiteralText("Removed " + GameProfileArgumentType.getProfileArgument(ctx, "username").stream().findFirst().get().getName() + " from the whitelist!"), true);
                            DatabaseManager.removePlayer(GameProfileArgumentType.getProfileArgument(ctx, "username").stream().findFirst().get());
                            return 1;
                        })
                ))
                .then(literal("check").then(CommandManager.argument("username", GameProfileArgumentType.gameProfile())
                        .requires(Permissions.require("fabricsqlwhitelister.check"))
                        .executes(ctx -> {
                            if(DatabaseManager.checkIfWhitelisted(GameProfileArgumentType.getProfileArgument(ctx, "username").stream().findFirst().get().getId())){
                                ctx.getSource().sendFeedback(new LiteralText(GameProfileArgumentType.getProfileArgument(ctx, "username").stream().findFirst().get().getName() + " is whitelisted!"), false);
                            }else if(!DatabaseManager.checkIfWhitelisted(GameProfileArgumentType.getProfileArgument(ctx, "username").stream().findFirst().get().getId())){
                                ctx.getSource().sendFeedback(new LiteralText(GameProfileArgumentType.getProfileArgument(ctx, "username").stream().findFirst().get().getName() + " is not whitelisted!"), false);
                            }
                            return 1;
                        })
                ))
                .then(literal("list").requires(Permissions.require("fabricsqlwhitelister.list")).executes(ctx -> {
                    DatabaseManager.listPlayers(ctx.getSource());
                    return 1;
                })
        )));
    }
}
