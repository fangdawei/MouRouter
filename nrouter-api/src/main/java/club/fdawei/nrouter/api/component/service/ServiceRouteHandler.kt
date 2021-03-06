package club.fdawei.nrouter.api.component.service

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import club.fdawei.nrouter.api.action.RouteActionBundle
import club.fdawei.nrouter.api.component.service.arg.ServiceOption
import club.fdawei.nrouter.api.route.RouteHandler
import club.fdawei.nrouter.api.route.RouteNodeInfo
import club.fdawei.nrouter.api.util.safeThrowException

/**
 * Created by david on 2019/05/30.
 */
class ServiceRouteHandler : RouteHandler {
    override fun go(data: RouteActionBundle, info: RouteNodeInfo?) {
        if (info == null) {
            safeThrowException("info is required ,but Null!")
            return
        }
        val context = data.args.get(Context::class, assignable = true)
        if (context == null) {
            safeThrowException("context is required, but Not Found!")
            return
        }
        val intent = Intent(context, info.target.java)
        when (data.args.get(ServiceOption::class)) {
            ServiceOption.BIND -> {
                val conn = data.args.get(ServiceConnection::class, assignable = true)
                if (conn != null) {
                    val flags = if (data.flags > 0) data.flags else Context.BIND_AUTO_CREATE
                    context.bindService(intent, conn, flags)
                }
            }
            ServiceOption.UNBIND -> {
                val conn = data.args.get(ServiceConnection::class, assignable = true)
                if (conn != null) {
                    context.unbindService(conn)
                }
            }
            ServiceOption.STOP -> {
                context.stopService(intent)
            }
            else -> {
                context.startService(intent)
            }
        }
    }
}