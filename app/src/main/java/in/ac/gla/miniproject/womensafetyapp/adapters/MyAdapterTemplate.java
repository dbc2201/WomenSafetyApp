package in.ac.gla.miniproject.womensafetyapp.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.ac.gla.miniproject.womensafetyapp.R;
import in.ac.gla.miniproject.womensafetyapp.models.Templates;

//import in.ac.gla.miniproject.models.Templates;

public class MyAdapterTemplate extends ArrayAdapter {

    Activity context;
    int resource;
    ArrayList<Templates> allList;
    DeleteListener deleteListener;

    public MyAdapterTemplate(Context context, int resource, ArrayList<Templates> allList) {
        super(context, resource, allList);
        this.context = (Activity) context;
        this.resource = resource;
        this.allList = allList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = context.getLayoutInflater().inflate(resource, parent, false);
        final Templates c = allList.get(position);
        ImageView deleteTemplate;
        TextView texttemplate = view.findViewById(R.id.textTemplate);
        texttemplate.setText(c.getMsg());
        deleteTemplate = view.findViewById(R.id.deleteTemplate);


        deleteTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AlertDialogCustom));

                dialog.setTitle("Confirm");
                dialog.setMessage("Are you sure you want to delete?");
                dialog.setIcon(android.R.drawable.ic_dialog_alert);

                dialog.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (deleteListener != null) {
                            deleteListener.deleteItem(c);
                            notifyDataSetChanged();
                        }


                    }
                });

                dialog.setNegativeButton(android.R.string.no, null);
                dialog.show();


            }
        });
        if (c.isActive()) {
            texttemplate.setTextColor(Color.GREEN);
        }
        return view;
    }

    public void setDeleteListener(DeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public interface DeleteListener {
        void deleteItem(Templates c);


    }


}

