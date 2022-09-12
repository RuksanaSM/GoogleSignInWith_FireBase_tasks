package com.example.googlesigninwith_firebase_task;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter_LifecycleAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyHolder>
{

    // creating variables for our ArrayList and context
    private ArrayList<ModelClass> modelClassArrayList;
    private Context context;
String titlestr,descstr;
    private FirebaseFirestore firebaseFirestore;
    AddingData_Activity addingData_activity;

    public AdapterClass(ArrayList<ModelClass> modelClassArrayList, Context context) {
        this.modelClassArrayList = modelClassArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.custom_listview, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder,  int position) {
        // setting data to our text views from our modal class.
        ModelClass modelClass = modelClassArrayList.get(position);
        holder.titletext.setText(modelClass.getTitle());
        holder.desc.setText(modelClass.getDesc());
      Glide.with(context).load(modelClassArrayList.get(position).getImg()).into(holder.imageView);


//        Glide.with(context)
//                .load(photoModelList.get(i).getImage())
//                .centerCrop()
//                .placeholder(R.drawable.loading)
//                .into(imageView);
    }


    @Override
    public int getItemCount() {
        return modelClassArrayList.size();
    }


    class  MyHolder extends RecyclerView.ViewHolder  {
        TextView titletext,desc;
        ImageView imageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            titletext=itemView.findViewById(R.id.disptitle);
            desc=itemView.findViewById(R.id.dispdesc);

            imageView=itemView.findViewById(R.id.recyclrimg);


            itemView.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         ModelClass modelClass = modelClassArrayList.get(getAdapterPosition());

//         AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
////                builder.setTitle("Notes");
//
//         // set the custom layout
//         final  View customLayout=LayoutInflater.from(context).inflate(R.layout.custom_dialog, (ViewGroup) view, false);
////                final View customLayout = getLayoutInflater().inflate(
////                        R.layout.custom_add_dialog,
////                        null);
//         builder.setView(customLayout);


             Intent i = new Intent(context,UpdateActivity.class);
             // below line is for putting our course object to our next activity.
            i.putExtra("fb", modelClass);
             context.startActivity(i);

//         Intent i=new Intent(context,UpdateActivity.class);
//         i.putExtra("id",modelClass.id);
//         i.putExtra("title",modelClass.title);
//         i.putExtra("desc",modelClass.desc);
//         context.startActivity(i);

//
//         AlertDialog dialog
//                 = builder.create();
//         dialog.show();
//

     }
 });
        }

    }

}
