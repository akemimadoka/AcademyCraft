/**
 * Copyright (c) Lambda Innovation, 2013-2016
 * This file is part of the AcademyCraft mod.
 * https://github.com/LambdaInnovation/AcademyCraft
 * Licensed under GPLv3, see project root for more information.
 */
package cn.academy.test.command;

import cn.academy.core.command.ACCommand;
import cn.academy.misc.media.ACMedia;
import cn.academy.misc.media.MediaManager;
import cn.academy.misc.media.MediaUtils;
import cn.academy.misc.media.OnlineMediaManager;
import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.annoreg.mc.RegCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;

/**
 * @author KSkun
 */
@Registrant
@RegCommand
public class CommandMediaTest extends ACCommand {

    public CommandMediaTest() {
        super("acmedia");
    }

    @Override
    public void processCommand(ICommandSender ics, String[] pars) {
        switch(pars[0]) {
            case "play":
                MediaUtils.playMedia(MediaManager.INSTANCE.getMedia(pars[1]), false);
                break;
            case "pause":
                MediaUtils.pauseMedia(MediaManager.INSTANCE.getMedia(pars[1]));
                break;
            case "volume":
                MediaUtils.setMediaVolume(MediaManager.INSTANCE.getMedia(pars[1]), Float.valueOf(pars[2]));
                break;
            case "stop":
                MediaUtils.stopMedia(MediaManager.INSTANCE.getMedia(pars[1]));
                break;
            case "medias":
                ics.addChatMessage(new ChatComponentTranslation(MediaManager.INSTANCE.getMediaIds().toString()));
                break;
            case "download":
                OnlineMediaManager.INSTANCE.downloadMedia(MediaManager.INSTANCE.getMedia(pars[1]));
                break;
            case "remove":
                OnlineMediaManager.INSTANCE.removeLocalMedia(MediaManager.INSTANCE.getMedia(pars[1]));
                break;
            case "info":
                ACMedia m = MediaManager.INSTANCE.getMedia(pars[1]);
                ics.addChatMessage(new ChatComponentTranslation("====AcademyCraft Media System====\nAuthor: " + m.getAuthor() +
                        "\nName: " + m.getName() + "\nFile: " + m.getFile().getPath() + "\nRemark: " + m.getRemark() +
                        "\nCover Picture: " + m.getCoverPic().getPath() + "\n================================="));

        }
    }

}
