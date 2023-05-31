package net.radio.streaming.players.mpd.tasks;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import net.radio.streaming.players.mpd.MPDAsyncTask;
import net.radio.streaming.players.mpd.MPDServerData;

public class MPDChangeVolumeTask extends MPDAsyncTask {
    @SuppressLint("DefaultLocale")
    public MPDChangeVolumeTask(final int deltaVolume, @Nullable FailureCallback failureCallback, MPDServerData server) {
        setStages(
                new MPDAsyncTask.ReadStage[]{
                        okReadStage(),
                        statusReadStage(true),
                        (task, result) -> {
                            task.getMpdServerData().updateStatus(result);
                            task.notifyServerUpdated();
                            return false;
                        },
                },
                new MPDAsyncTask.WriteStage[]{
                        statusWriteStage(),
                        (task, bufferedWriter) -> {
                            int newVolume = Math.min(Math.max(task.getMpdServerData().volume + deltaVolume, 0), 100);
                            bufferedWriter.write(String.format("command_list_begin\nsetvol %d\nstatus\ncommand_list_end\n", newVolume));
                            return true;
                        },
                }, failureCallback);
    }
}
