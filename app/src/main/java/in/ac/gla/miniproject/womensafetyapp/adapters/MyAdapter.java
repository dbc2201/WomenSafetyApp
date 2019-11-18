package in.ac.gla.miniproject.womensafetyapp.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import in.ac.gla.miniproject.womensafetyapp.R;
import in.ac.gla.miniproject.womensafetyapp.models.Contacts;

public class MyAdapter extends ArrayAdapter {
    ImageView deleteContact;
    Activity context;
    int resource;
    ArrayList<Contacts> allList;
    DeleteListener deleteListener;

    public MyAdapter(Context context, int resource, ArrayList<Contacts> allList) {
        super(context, resource, allList);
        this.context = (Activity) context;
        this.resource = resource;
        this.allList = allList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = context.getLayoutInflater().inflate(resource, parent, false);
        final Contacts c = allList.get(position);
        TextView name = view.findViewById(R.id.t1);
        TextView number = view.findViewById(R.id.t2);
        deleteContact = view.findViewById(R.id.deleteContact);
        name.setText(c.getName());
        number.setText(c.getNumber());


        deleteContact.setOnClickListener(new View.OnClickListener() {
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


        return view;
    }

    public void setDeleteListener(DeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public interface DeleteListener {
        void deleteItem(Contacts c);


    }


}

