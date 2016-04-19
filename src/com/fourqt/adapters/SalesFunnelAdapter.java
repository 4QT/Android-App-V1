package com.fourqt.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fourqt.model.SalesFunnelEn;
import com.fourqt.view.R;

public class SalesFunnelAdapter extends BaseAdapter {

	private List<SalesFunnelEn> mSelesFunnel = new ArrayList<SalesFunnelEn>();
	private LayoutInflater mLnflInflater = null;
	private Context mContext;

	public SalesFunnelAdapter(Context c, List<SalesFunnelEn> _myData) {
		this.mSelesFunnel = _myData;
		this.mLnflInflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = c;
	}

	@Override
	public int getCount() {
		return this.mSelesFunnel.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup view) {
		MenuAdapterHolder holder;

		if (convertView == null) {
			holder = new MenuAdapterHolder();
			convertView = this.mLnflInflater.inflate(
					R.layout.salesfunnel_adapter, null);
			holder._title = (TextView) convertView.findViewById(R.id.textView2);
			holder._subTitle = (TextView) convertView
					.findViewById(R.id.textView3);
			holder._lead = (TextView) convertView.findViewById(R.id.textView4);
			holder._value = (TextView) convertView.findViewById(R.id.textView5);
			convertView.setTag(holder);
		} else {
			holder = (MenuAdapterHolder) convertView.getTag();
		}

		holder._title.setText(mSelesFunnel.get(position).getTitle());
		holder._subTitle.setText(mSelesFunnel.get(position).getSubTitle());
		holder._value.setText(mSelesFunnel.get(position).getValue());
		holder._lead.setText(mSelesFunnel.get(position).getLead());

		return convertView;
	}

	class MenuAdapterHolder {
		TextView _title;
		TextView _subTitle;
		TextView _value;
		TextView _lead;
	}

}
