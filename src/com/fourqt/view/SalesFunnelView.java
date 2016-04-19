package com.fourqt.view;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.fourqt.adapters.SalesFunnelAdapter;
import com.fourqt.main.RootActivity;
import com.fourqt.model.SalesFunnelEn;
import com.fourqt.view.R;

public class SalesFunnelView extends RootActivity{

	private ListView listView;
	public SalesFunnelView() {
		super();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sales_funnel_master);
		
		Button mBtnLeft = (Button) findViewById(R.id.btn_left);
		mBtnLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggle();
			}
		});
		listView = (ListView)findViewById(R.id.salesfunnel_listview);
		SalesFunnelAdapter adapter = new SalesFunnelAdapter(SalesFunnelView.this, dummyData());
		
		listView.setAdapter(adapter);
	}
	
	private List<SalesFunnelEn> dummyData()
	{
		 List<SalesFunnelEn> list = new ArrayList<SalesFunnelEn>();
		 SalesFunnelEn entity;
		 
		 entity=new SalesFunnelEn();
		 entity.setLead("100");
		 entity.setSubTitle("Mobile application developer");
		 entity.setTitle("Naveen Kumar BV");
		 entity.setValue("100.00L");
		 list.add(entity);
		 
		 entity=new SalesFunnelEn();
		 entity.setLead("100");
		 entity.setSubTitle("Mobile application developer");
		 entity.setTitle("Naveen Kumar BV");
		 entity.setValue("100.00L");
		 list.add(entity);
		 
		 return list;
	}
}
