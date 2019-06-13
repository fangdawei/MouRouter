package club.fdawei.mourouter.api.action

import android.os.Bundle
import android.os.Parcelable
import club.fdawei.mourouter.api.base.DataContainer

/**
 * Created by david on 2019/06/04.
 */
abstract class AbsAction<H> : Action<H>, ActionData {

    abstract val host: H
    override var flags = 0
    override val extras: Bundle = Bundle()
    override val envs = DataContainer()

    override fun env(value: Any): H {
        this.envs.put(value)
        return host
    }

    override fun env(container: DataContainer): H {
        this.envs.putAll(container)
        return host
    }

    override fun withFlags(vararg flag: Int): H {
        flag.forEach {
            this.flags = this.flags or it
        }
        return host
    }

    override fun withBundle(bundle: Bundle): H {
        this.extras.putAll(bundle)
        return host
    }

    override fun withString(k: String, v: String): H {
        this.extras.putString(k, v)
        return host
    }

    override fun withStringArrayList(k: String, v: ArrayList<String>): H {
        this.extras.putStringArrayList(k, v)
        return host
    }

    override fun withInt(k: String, v: Int): H {
        this.extras.putInt(k, v)
        return host
    }

    override fun withIntArrayList(k: String, v: ArrayList<Int>): H {
        this.extras.putIntegerArrayList(k, v)
        return host
    }

    override fun withLong(k: String, v: Long): H {
        this.extras.putLong(k, v)
        return host
    }

    override fun withBoolean(k: String, v: Boolean): H {
        this.extras.putBoolean(k, v)
        return host
    }

    override fun withParcelable(k: String, v: Parcelable): H {
        this.extras.putParcelable(k, v)
        return host
    }

    override fun withParcelableArrayList(k: String, v: ArrayList<out Parcelable>): H {
        this.extras.putParcelableArrayList(k, v)
        return host
    }
}