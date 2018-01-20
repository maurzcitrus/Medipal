package sg.edu.nus.iss.ft08.medipal.medication;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import sg.edu.nus.iss.ft08.medipal.R;
import sg.edu.nus.iss.ft08.medipal.core.Consumption;
import sg.edu.nus.iss.ft08.medipal.core.Medication;
import sg.edu.nus.iss.ft08.medipal.core.Medicine;
import sg.edu.nus.iss.ft08.medipal.medication.MedicationFragment.OnConsumeListener;
import sg.edu.nus.iss.ft08.medipal.medication.MedicationFragment.OnMedicineDetailListener;


public class MedicationRecyclerViewAdapter extends RecyclerView.Adapter<MedicationRecyclerViewAdapter.ViewHolder> {

    private final MedicationFragment.OnMedicineDetailListener medicineDetailListener;
    private final OnConsumeListener consumptionListener;

    private List<Medication> medications;
    private Context context;

    public MedicationRecyclerViewAdapter(Context context,
                                         OnMedicineDetailListener medicineDetailListener,
                                         OnConsumeListener consumptionListener) {
        this.context = context;
        medications = findAllMedication(context);
        this.medicineDetailListener = medicineDetailListener;
        this.consumptionListener = consumptionListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_medication, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Medication current = medications.get(position);

        holder.mItem = current;
        holder.mMedicineName.setText(current.getMedicineName());
        holder.mMedicineStatus.setText(current.getMedicineStatus());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != medicineDetailListener) {
                    medicineDetailListener.onViewMedicineDetail(holder.mItem);
                }
            }
        });


        LinearLayout pnl = holder.mFrequencyPanel;
        if (pnl.getChildCount() == 0) {
            Medicine medicine = holder.mItem.getMedicine();
            List<Consumption> consumptions = medicine.getTodayConsumptions();
            List<ImageButton> buttons = new ArrayList<>();

            for (int i = 0; i < medicine.getFrequency(); i++) {

                boolean isConsumed = (consumptions.size() > i);
                ImageButton b = createImageButton(i, isConsumed);
                if (!isConsumed) {
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateConsumption(v, holder.mItem);
                        }
                    });
                }

                buttons.add(b);
            }

            prepareImageButtonRows(pnl, buttons);
        }
    }

    private void prepareImageButtonRows(LinearLayout pnl, List<ImageButton> buttons) {
        LinearLayout.LayoutParams btnPnlParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int currentIndex = 0;
        int batchSize = 5;
        do {
            LinearLayout btnPnl = new LinearLayout(context);
            btnPnl.setLayoutParams(btnPnlParams);
            btnPnl.setOrientation(LinearLayout.HORIZONTAL);

            for (int i = 0; i < batchSize; i++) {
                if (currentIndex < buttons.size()) {
                    btnPnl.addView(buttons.get(currentIndex));
                } else {
                    break;
                }
                currentIndex++;
            }

            pnl.addView(btnPnl);
        } while (currentIndex < buttons.size());
    }

    private void updateConsumption(View view, Medication medication) {
        ImageButton button = (ImageButton) view;

        if (button != null) {

            Medicine medicine = medication.getMedicine();


            if (medicine.isConsumable()) {
                button.setImageResource(R.drawable.ic_check_circle_black_24dp);
                button.setImageTintList(context.getColorStateList(R.color.colorPrimary));

                button.setOnClickListener(null);
            }

            consumptionListener.onConsumeMedicine(medication);

        }
    }


    private ImageButton createImageButton(int buttonIndex, boolean isConsumed) {

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layoutParams.setMargins(10, 0, 0, 0);

        ImageButton b = new ImageButton(context);
        b.setLayoutParams(layoutParams);
        b.setBackground(null);
        b.setTag(Integer.toString(buttonIndex));

        if (isConsumed) {
            b.setImageResource(R.drawable.ic_check_circle_black_24dp);
            b.setImageTintList(context.getColorStateList(R.color.colorPrimary));
        } else {
            b.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp);
            b.setImageTintList(context.getColorStateList(R.color.colorInactive));
        }

        return b;
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    public void refresh(Context context) {
        medications = findAllMedication(context);
        notifyDataSetChanged();
    }

    private List<Medication> findAllMedication(Context context) {
        List<Medication> records = null;

        try {
            FindAllQuery query = new FindAllQuery(context);
            query.execute();
            records = query.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (records == null) {
            records = new ArrayList<Medication>();
        }

        return records;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mMedicineName;
        public final TextView mMedicineStatus;
        public final LinearLayout mFrequencyPanel;
        public final List<ImageButton> mButtons;
        public Medication mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMedicineName = (TextView) view.findViewById(R.id.medicine_name);
            mMedicineStatus = (TextView) view.findViewById(R.id.medicine_status);
            mButtons = new ArrayList<ImageButton>();
            mFrequencyPanel = (LinearLayout) view.findViewById(R.id.medicine_frequency_pnl);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMedicineName.getText() + "'";
        }
    }
}
