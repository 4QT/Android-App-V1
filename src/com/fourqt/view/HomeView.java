package com.fourqt.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fourqt.main.RootActivity;
import com.fourqt.model.Budget;
import com.fourqt.model.Caste;
import com.fourqt.model.Caste_Category;
import com.fourqt.model.City;
import com.fourqt.model.Communication;
import com.fourqt.model.Direction_Facing;
import com.fourqt.model.DumpReason;
import com.fourqt.model.EmailMessage;
import com.fourqt.model.EnquiryFrom;
import com.fourqt.model.EnquirySource;
import com.fourqt.model.EnquiryStage;
import com.fourqt.model.EnquiryType;
import com.fourqt.model.Floor_Preference;
import com.fourqt.model.GetAllLeadListResult;
import com.fourqt.model.HomeGridEn;
import com.fourqt.model.ListAllEnquiryMastersResult;
import com.fourqt.model.Locality;
import com.fourqt.model.Location;
import com.fourqt.model.MainFollowupType;
import com.fourqt.model.NearBy_Landmark;
import com.fourqt.model.Profession;
import com.fourqt.model.ProjectLocation;
import com.fourqt.model.ProjectUnitType;
import com.fourqt.model.PropertyType;
import com.fourqt.servicerequest.AsyncOperation;
import com.fourqt.servicerequest.ServerCallback;
import com.fourqt.tab.EnquiryFollowupMainFr;
import com.fourqt.util.CommonContexts;
import com.fourqt.util.GsonSingleton;
import com.fourqt.util.SystemUtil;
import com.fourqt.view.R;

public class HomeView extends RootActivity implements ServerCallback {

	private GridView gridView;
	private static int pagesize = 20, pageindex = 1;
	private static boolean isMasterCalled = false;
	private static boolean isProjectUnitCalled = false;
	private static boolean isMainEnquiryList = false;
	private static boolean isMainEnquiryTrans = false;

	public HomeView() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homeview_grid);
		gridView = (GridView) findViewById(R.id.gridview1);
		Button mBtnLeft = (Button) findViewById(R.id.btn_left);
		mBtnLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				toggle();
			}
		});

		final Integer[] mThumbIds12 = { R.drawable.todayfollowup,
				R.drawable.newfollowup, R.drawable.transferredfollowup,
				R.drawable.pendingfollowup, R.drawable.futurefollowup,
				R.drawable.newenquiry, R.drawable.searchenquiry,
				R.drawable.callmanagement, R.drawable.enquirycalendar,
				R.drawable.unitstatus, R.drawable.salesapproval,
				R.drawable.constructionapproval, R.drawable.salesfunnel,
				R.drawable.checkin, R.drawable.checkout, R.drawable.misreport,
				R.drawable.administration, R.drawable.logout, };

		gridView.setAdapter(new HomeViewGridAdapater(HomeView.this,
				getList(mThumbIds12)));

		isMasterCalled = true;
		isMainEnquiryList = false;
		isMainEnquiryTrans = false;
		isProjectUnitCalled = false;
		String url = "" + getString(R.string.service_baseurl)
				+ "ListAllEnquiryMasters?Token=" + CommonContexts.appkey + "";
		CallServer(url);

	}

	private List<HomeGridEn> getList(Integer[] images) {
		List<HomeGridEn> list = new ArrayList<HomeGridEn>();

		HomeGridEn hge = new HomeGridEn();
		hge.setImagePath(images[0]);
		hge.setTitle("todayfollowup");
		list.add(hge);

		HomeGridEn hge1 = new HomeGridEn();
		hge1.setImagePath(images[1]);
		hge1.setTitle("newfollowup");
		list.add(hge1);

		HomeGridEn hge2 = new HomeGridEn();
		hge2.setImagePath(images[2]);
		hge2.setTitle("transferredfollowup");
		list.add(hge2);

		HomeGridEn hge3 = new HomeGridEn();
		hge3.setImagePath(images[3]);
		hge3.setTitle("pendingfollowup");
		list.add(hge3);

		HomeGridEn hge4 = new HomeGridEn();
		hge4.setImagePath(images[4]);
		hge4.setTitle("futurefollowup");
		list.add(hge4);
		HomeGridEn hge5 = new HomeGridEn();
		hge5.setImagePath(images[5]);
		hge5.setTitle("newenquiry");
		list.add(hge5);

		HomeGridEn hge6 = new HomeGridEn();
		hge6.setImagePath(images[6]);
		hge6.setTitle("searchenquiry");
		list.add(hge6);

		HomeGridEn hge7 = new HomeGridEn();
		hge7.setImagePath(images[7]);
		hge7.setTitle("callmanagement");
		list.add(hge7);

		HomeGridEn hge8 = new HomeGridEn();
		hge8.setImagePath(images[8]);
		hge8.setTitle("enquirycalendar");
		list.add(hge8);

		HomeGridEn hge9 = new HomeGridEn();
		hge9.setImagePath(images[9]);
		hge9.setTitle("unitstatus");
		list.add(hge9);

		HomeGridEn hge10 = new HomeGridEn();
		hge10.setImagePath(images[10]);
		hge10.setTitle("salesapproval");
		list.add(hge10);

		HomeGridEn hge11 = new HomeGridEn();
		hge11.setImagePath(images[11]);
		hge11.setTitle("constructionapproval");
		list.add(hge11);
		HomeGridEn hge12 = new HomeGridEn();
		hge12.setImagePath(images[12]);
		hge12.setTitle("salesfunnel");
		list.add(hge12);

		HomeGridEn hge13 = new HomeGridEn();
		hge13.setImagePath(images[13]);
		hge13.setTitle("checkin");
		list.add(hge13);

		HomeGridEn hge14 = new HomeGridEn();
		hge14.setImagePath(images[14]);
		hge14.setTitle("checkout");
		list.add(hge14);
		HomeGridEn hge15 = new HomeGridEn();
		hge15.setImagePath(images[15]);
		hge15.setTitle("misreport");
		list.add(hge15);

		HomeGridEn hge16 = new HomeGridEn();
		hge16.setImagePath(images[16]);
		hge16.setTitle("administration");
		list.add(hge16);

		HomeGridEn hge17 = new HomeGridEn();
		hge17.setImagePath(images[17]);
		hge17.setTitle("logout");
		list.add(hge17);

		return list;

	}

	private class HomeViewGridAdapater extends BaseAdapter {

		private List<HomeGridEn> mListHomeGrid = new ArrayList<HomeGridEn>();
		private LayoutInflater mLnflInflater = null;
		private Context mContext;

		public HomeViewGridAdapater(Context c, List<HomeGridEn> listData) {
			this.mListHomeGrid = listData;
			this.mLnflInflater = (LayoutInflater) c
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mContext = c;
		}

		@Override
		public int getCount() {
			return this.mListHomeGrid.size();
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
						R.layout.homeview_grid_adapter, null);
				holder.image = (ImageView) convertView
						.findViewById(R.id.imageView1);
				holder.lineBottom = (TextView) convertView
						.findViewById(R.id.textView3);
				holder.lineRight = (TextView) convertView
						.findViewById(R.id.textView2);
				convertView.setTag(holder);
			} else {
				holder = (MenuAdapterHolder) convertView.getTag();
			}
			holder.image.setImageResource(mListHomeGrid.get(position)
					.getImagePath());
			if ((position + 1) % 3 == 0) {
				holder.lineRight.setVisibility(View.INVISIBLE);
			} else {
				holder.lineRight.setVisibility(View.VISIBLE);
			}

			if (mListHomeGrid.size() - position <= 3) {
				holder.lineBottom.setVisibility(View.INVISIBLE);
			} else {
				holder.lineBottom.setVisibility(View.VISIBLE);
			}

			holder.image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					isMasterCalled = false;
					isMainEnquiryList = false;
					isMainEnquiryTrans = true;
					isProjectUnitCalled = false;
					if (mListHomeGrid.get(position).getTitle()
							.equalsIgnoreCase("todayfollowup")
							|| mListHomeGrid.get(position).getTitle()
									.equalsIgnoreCase("pendingfollwup")
							|| mListHomeGrid.get(position).getTitle()
									.equalsIgnoreCase("futurefollowup")
							|| mListHomeGrid.get(position).getTitle()
									.equalsIgnoreCase("newfollowup")
							|| mListHomeGrid.get(position).getTitle()
									.equalsIgnoreCase("transferredfollowup")) {

						isMasterCalled = false;
						isProjectUnitCalled = false;
						isMainEnquiryList = true;
						isMainEnquiryTrans = false;
						String url = "" + getString(R.string.service_baseurl)
								+ "GetAllLeadList?Token="
								+ CommonContexts.appkey + "&LoginID="
								+ CommonContexts.clr.getLoginID()
								+ "&PageIndex=" + pageindex + "&PageSize="
								+ pagesize + "&Type=A";
						CallServer(url);

					}
				}
			});

			return convertView;
		}

		class MenuAdapterHolder {
			TextView lineRight;
			TextView lineBottom;
			ImageView image;
		}

	}

	private void CallServer(String url) {
		if (SystemUtil.haveNetworkConnection(HomeView.this)) {
			AsyncOperation as = new AsyncOperation(this,
					CommonContexts.CONNECTION_GET);
			as.execute(url);

		} else {
			Toast.makeText(HomeView.this, R.string.no_network,
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void doPostExecute(String serverResponse) throws JSONException {
		CommonContexts.dismissProgressDialog();
		if (serverResponse != null) {

			if (isMasterCalled && !isMainEnquiryList && !isMainEnquiryTrans && !isProjectUnitCalled) {

				System.out.println("Master List : " + serverResponse);
				JSONObject jOb = new JSONObject(serverResponse);
				if (jOb.has("ListAllEnquiryMastersResult")) {
					ListAllEnquiryMastersResult master = new ListAllEnquiryMastersResult();
					String jString = jOb
							.getString("ListAllEnquiryMastersResult");
					JSONObject jObje = new JSONObject(jString);
					if (jObje.has("Budget")) {
						JSONArray jBudget = jObje.getJSONArray("Budget");
						if (jBudget != null && jBudget.length() > 0) {
							List<Budget> listBudget = new ArrayList<Budget>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								Budget budget = GsonSingleton.getInstance()
										.fromJson(res, Budget.class);
								listBudget.add(budget);
							}
							master.setBudgetList(listBudget);
						}
					}

					if (jObje.has("Caste")) {
						JSONArray jBudget = jObje.getJSONArray("Caste");
						if (jBudget != null && jBudget.length() > 0) {
							List<Caste> listBudget = new ArrayList<Caste>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								Caste budget = GsonSingleton.getInstance()
										.fromJson(res, Caste.class);
								listBudget.add(budget);
							}
							master.setCastetList(listBudget);
						}
					}

					if (jObje.has("Caste_Category")) {
						JSONArray jBudget = jObje
								.getJSONArray("Caste_Category");
						if (jBudget != null && jBudget.length() > 0) {
							List<Caste_Category> listBudget = new ArrayList<Caste_Category>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								Caste_Category budget = GsonSingleton
										.getInstance().fromJson(res,
												Caste_Category.class);
								listBudget.add(budget);
							}
							master.setCasteCategoryList(listBudget);
						}
					}
					if (jObje.has("City")) {
						JSONArray jBudget = jObje.getJSONArray("City");
						if (jBudget != null && jBudget.length() > 0) {
							List<City> listBudget = new ArrayList<City>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								City budget = GsonSingleton.getInstance()
										.fromJson(res, City.class);
								listBudget.add(budget);
							}
							master.setCityList(listBudget);
						}
					}

					if (jObje.has("Communication")) {
						JSONArray jBudget = jObje.getJSONArray("Communication");
						if (jBudget != null && jBudget.length() > 0) {
							List<Communication> listBudget = new ArrayList<Communication>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								Communication budget = GsonSingleton
										.getInstance().fromJson(res,
												Communication.class);
								listBudget.add(budget);
							}
							master.setCommunicationList(listBudget);
						}
					}

					if (jObje.has("Direction_Facing")) {
						JSONArray jBudget = jObje
								.getJSONArray("Direction_Facing");
						if (jBudget != null && jBudget.length() > 0) {
							List<Direction_Facing> listBudget = new ArrayList<Direction_Facing>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								Direction_Facing budget = GsonSingleton
										.getInstance().fromJson(res,
												Direction_Facing.class);
								listBudget.add(budget);
							}
							master.setDirectionFacingList(listBudget);
						}
					}

					if (jObje.has("DumpReason")) {
						JSONArray jBudget = jObje.getJSONArray("DumpReason");
						if (jBudget != null && jBudget.length() > 0) {
							List<DumpReason> listBudget = new ArrayList<DumpReason>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								DumpReason budget = GsonSingleton.getInstance()
										.fromJson(res, DumpReason.class);
								listBudget.add(budget);
							}
							master.setDumpReasonList(listBudget);
						}
					}

					if (jObje.has("EnquiryFrom")) {
						JSONArray jBudget = jObje.getJSONArray("EnquiryFrom");
						if (jBudget != null && jBudget.length() > 0) {
							List<EnquiryFrom> listBudget = new ArrayList<EnquiryFrom>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								EnquiryFrom budget = GsonSingleton
										.getInstance().fromJson(res,
												EnquiryFrom.class);
								listBudget.add(budget);
							}
							master.setEnquiryFormList(listBudget);
						}
					}

					if (jObje.has("EnquirySource")) {
						JSONArray jBudget = jObje.getJSONArray("EnquirySource");
						if (jBudget != null && jBudget.length() > 0) {
							List<EnquirySource> listBudget = new ArrayList<EnquirySource>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								EnquirySource budget = GsonSingleton
										.getInstance().fromJson(res,
												EnquirySource.class);
								listBudget.add(budget);
							}
							master.setEnquirySourceList(listBudget);
						}
					}

					if (jObje.has("EnquiryStage")) {
						JSONArray jBudget = jObje.getJSONArray("EnquiryStage");
						if (jBudget != null && jBudget.length() > 0) {
							List<EnquiryStage> listBudget = new ArrayList<EnquiryStage>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								EnquiryStage budget = GsonSingleton
										.getInstance().fromJson(res,
												EnquiryStage.class);
								listBudget.add(budget);
							}
							master.setEnquiryStageList(listBudget);
						}
					}

					if (jObje.has("EnquiryType")) {
						JSONArray jBudget = jObje.getJSONArray("EnquiryType");
						if (jBudget != null && jBudget.length() > 0) {
							List<EnquiryType> listBudget = new ArrayList<EnquiryType>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								EnquiryType budget = GsonSingleton
										.getInstance().fromJson(res,
												EnquiryType.class);
								listBudget.add(budget);
							}
							master.setEnquiryTypeList(listBudget);
						}
					}

					if (jObje.has("Floor_Preference")) {
						JSONArray jBudget = jObje
								.getJSONArray("Floor_Preference");
						if (jBudget != null && jBudget.length() > 0) {
							List<Floor_Preference> listBudget = new ArrayList<Floor_Preference>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								Floor_Preference budget = GsonSingleton
										.getInstance().fromJson(res,
												Floor_Preference.class);
								listBudget.add(budget);
							}
							master.setFloorPreferenceList(listBudget);
						}
					}

					if (jObje.has("Locality")) {
						JSONArray jBudget = jObje.getJSONArray("Locality");
						if (jBudget != null && jBudget.length() > 0) {
							List<Locality> listBudget = new ArrayList<Locality>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								Locality budget = GsonSingleton.getInstance()
										.fromJson(res, Locality.class);
								listBudget.add(budget);
							}
							master.setLocalityList(listBudget);
						}
					}

					if (jObje.has("Location")) {
						JSONArray jBudget = jObje.getJSONArray("Location");
						if (jBudget != null && jBudget.length() > 0) {
							List<Location> listBudget = new ArrayList<Location>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								Location budget = GsonSingleton.getInstance()
										.fromJson(res, Location.class);
								listBudget.add(budget);
							}
							master.setLocationList(listBudget);
						}
					}

					if (jObje.has("MainFollowupType")) {
						JSONArray jBudget = jObje
								.getJSONArray("MainFollowupType");
						if (jBudget != null && jBudget.length() > 0) {
							List<MainFollowupType> listBudget = new ArrayList<MainFollowupType>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								MainFollowupType budget = GsonSingleton
										.getInstance().fromJson(res,
												MainFollowupType.class);
								listBudget.add(budget);
							}
							master.setMainFollowupTypeList(listBudget);
						}
					}

					if (jObje.has("NearBy_Landmark")) {
						JSONArray jBudget = jObje
								.getJSONArray("NearBy_Landmark");
						if (jBudget != null && jBudget.length() > 0) {
							List<NearBy_Landmark> listBudget = new ArrayList<NearBy_Landmark>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								NearBy_Landmark budget = GsonSingleton
										.getInstance().fromJson(res,
												NearBy_Landmark.class);
								listBudget.add(budget);
							}
							master.setNearbyLandmarkList(listBudget);
						}
					}
					if (jObje.has("Profession")) {
						JSONArray jBudget = jObje.getJSONArray("Profession");
						if (jBudget != null && jBudget.length() > 0) {
							List<Profession> listBudget = new ArrayList<Profession>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								Profession budget = GsonSingleton.getInstance()
										.fromJson(res, Profession.class);
								listBudget.add(budget);
							}
							master.setProfessionList(listBudget);
						}
					}
					if (jObje.has("EmailMessage")) {
						JSONArray jBudget = jObje.getJSONArray("EmailMessage");
						if (jBudget != null && jBudget.length() > 0) {
							List<EmailMessage> listBudget = new ArrayList<EmailMessage>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								EmailMessage budget = GsonSingleton.getInstance()
										.fromJson(res, EmailMessage.class);
								listBudget.add(budget);
							}
							master.setEmailMessageList(listBudget);
						}
					}
					
					if (jObje.has("PropertyType")) {
						JSONArray jBudget = jObje.getJSONArray("PropertyType");
						if (jBudget != null && jBudget.length() > 0) {
							List<PropertyType> listBudget = new ArrayList<PropertyType>();
							for (int i = 0; i < jBudget.length(); i++) {
								String res = jBudget.getString(i);
								PropertyType budget = GsonSingleton.getInstance()
										.fromJson(res, PropertyType.class);
								listBudget.add(budget);
							}
							master.setPropertyTypeList(listBudget);
						}
					}

					CommonContexts.master = master;
				}
				isMasterCalled = false;
				isProjectUnitCalled = true;
				isMainEnquiryList = false;
				isMainEnquiryTrans = false;
				String url = "" + getString(R.string.service_baseurl)
						+ "ListProjectUnit?Token=" + CommonContexts.appkey
						+ "&LoginID=" + CommonContexts.clr.getLoginID() + "";
				CallServer(url);

			} else if (!isMasterCalled && !isMainEnquiryList && !isMainEnquiryTrans && isProjectUnitCalled) {
				if (CommonContexts.master != null) {
					JSONObject jOb = new JSONObject(serverResponse);
					if (jOb.has("ListProjectUnitResult")) {
						String jString = jOb.getString("ListProjectUnitResult");
						JSONObject jObj = new JSONObject(jString);

						if (jObj.has("ProjectUnitType")) {
							JSONArray jBudget = jObj
									.getJSONArray("ProjectUnitType");
							if (jBudget != null && jBudget.length() > 0) {
								List<ProjectUnitType> listBudget = new ArrayList<ProjectUnitType>();
								for (int i = 0; i < jBudget.length(); i++) {
									String res = jBudget.getString(i);
									ProjectUnitType budget = GsonSingleton
											.getInstance().fromJson(res,
													ProjectUnitType.class);
									listBudget.add(budget);
								}
								CommonContexts.master
										.setProjectUnitType(listBudget);
							}
						}
						if(jObj.has("ProjectLocation"))
						{
							JSONArray jBudget = jObj
									.getJSONArray("ProjectLocation");
							if (jBudget != null && jBudget.length() > 0) {
								List<ProjectLocation> listBudget = new ArrayList<ProjectLocation>();
								for (int i = 0; i < jBudget.length(); i++) {
									String res = jBudget.getString(i);
									ProjectLocation budget = GsonSingleton
											.getInstance().fromJson(res,
													ProjectLocation.class);
									listBudget.add(budget);
								}
								CommonContexts.master
										.setProjectLocationList(listBudget);
							}
						}
					}
				}
			} else if(!isMasterCalled && isMainEnquiryList && !isMainEnquiryTrans && !isProjectUnitCalled) {
				List<GetAllLeadListResult> list = new ArrayList<GetAllLeadListResult>();
				JSONObject jObject = new JSONObject(serverResponse);
				if (jObject.has("GetAllLeadListResult")) {
					String jString = jObject.getString("GetAllLeadListResult");
					JSONObject jObje = new JSONObject(jString);
					if (jObje.has("LstEnquiry")) {
						JSONArray jArray = jObje.getJSONArray("LstEnquiry");
						if (jArray != null && jArray.length() > 0) {
							for (int i = 0; i < jArray.length(); i++) {
								String res = jArray.getString(i);
								GetAllLeadListResult getAllleadList = GsonSingleton
										.getInstance().fromJson(res,
												GetAllLeadListResult.class);
								list.add(getAllleadList);
							}
						}
					}
				}
				if (list.size() > 0) {
					CommonContexts.listFollowup = list;
					
				}
				
				isMasterCalled = false;
				isProjectUnitCalled = false;
				isMainEnquiryList = false;
				isMainEnquiryTrans = true;
				String url = "" + getString(R.string.service_baseurl)
						+ "GetTransferredLeadList?Token="
						+ CommonContexts.appkey + "&LoginID="
						+ CommonContexts.clr.getLoginID()
						+ "&PageIndex=" + pageindex + "&PageSize="
						+ pagesize + "&Type=A";
				CallServer(url);
				
			}else if(!isMasterCalled && !isMainEnquiryList && isMainEnquiryTrans && !isProjectUnitCalled)
			{
				if(CommonContexts.listFollowup == null )
					CommonContexts.listFollowup = new ArrayList<GetAllLeadListResult>();
				JSONObject jObject = new JSONObject(serverResponse);
				if (jObject.has("GetTransferredLeadListResult")) {
					String jString = jObject.getString("GetTransferredLeadListResult");
					JSONObject jObje = new JSONObject(jString);
					if (jObje.has("LstEnquiry")) {
						JSONArray jArray = jObje.getJSONArray("LstEnquiry");
						if (jArray != null && jArray.length() > 0) {
							for (int i = 0; i < jArray.length(); i++) {
								String res = jArray.getString(i);
								GetAllLeadListResult getAllleadList = GsonSingleton
										.getInstance().fromJson(res,
												GetAllLeadListResult.class);
								CommonContexts.listFollowup.add(getAllleadList);
							}
						}
					}
				}
				if (CommonContexts.listFollowup.size() > 0) {
					Intent intent = new Intent(HomeView.this,
							EnquiryFollowupMainFr.class);
					startActivity(intent);
				}
			}
		}

	}

	@Override
	public void doPreExecute() {
		CommonContexts.showProgress(HomeView.this, "Please wait...");
	}

}
