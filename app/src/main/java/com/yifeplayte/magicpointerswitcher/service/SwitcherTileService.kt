package com.yifeplayte.magicpointerswitcher.service

import android.content.Intent
import android.hardware.input.InputManager
import android.os.IBinder
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import com.yifeplayte.magicpointerswitcher.R
import org.lsposed.hiddenapibypass.HiddenApiBypass

class SwitcherTileService : TileService() {

    companion object;

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return super.onBind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }

    override fun onClick() {
        super.onClick()
        try {
            val im = HiddenApiBypass.invoke(
                InputManager::class.java,
                null,
                "getInstance"
            )
            var state = HiddenApiBypass.invoke(
                InputManager::class.java,
                im,
                "getPointerVisibility"
            )
            state = state != true
            HiddenApiBypass.invoke(
                InputManager::class.java,
                im,
                "setPointerVisibility",
                state
            )
            qsTile.state = if (state) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
            qsTile.updateTile()
        } catch (e: Exception) {
            qsTile.state = Tile.STATE_UNAVAILABLE
            qsTile.updateTile()
        }
    }

    override fun onStartListening() {
        super.onStartListening()
        try {
            val im = HiddenApiBypass.invoke(
                InputManager::class.java,
                null,
                "getInstance"
            )
            val state = HiddenApiBypass.invoke(
                InputManager::class.java,
                im,
                "getPointerVisibility"
            )
            qsTile.state = if (state == true) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
            qsTile.updateTile()
        } catch (e: Exception) {
            qsTile.state = Tile.STATE_UNAVAILABLE
            qsTile.label = getString(R.string.not_available)
            qsTile.updateTile()
        }
    }

}