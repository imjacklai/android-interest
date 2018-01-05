package tw.ctl.interest.history

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.RealmResults
import kotlinx.android.synthetic.main.item_entity.view.*
import tw.ctl.interest.Entity
import tw.ctl.interest.R

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var entities: RealmResults<Entity>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_entity, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        if (entities?.get(position) != null) {
            holder.bindEntity(entities!![position]!!)
        }
    }

    override fun getItemCount(): Int = entities?.size ?: 0

    fun setEntities(entities: RealmResults<Entity>) {
        this.entities = entities
        notifyDataSetChanged()
    }

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindEntity(entity: Entity) {
            with(entity) {
                itemView.simpleInterest.text = entity.simpleResult
                itemView.compoundInterest.text = entity.compoundResult
                itemView.investInterest.text = entity.investResult
                itemView.principal.text = entity.principal
                itemView.interest.text = entity.interest
                itemView.period.text = entity.period
                itemView.invest.text = entity.invest
            }
        }
    }

}
