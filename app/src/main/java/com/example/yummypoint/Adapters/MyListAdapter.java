package com.example.yummypoint.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yummypoint.Model.APIUser;
import com.example.yummypoint.R;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{

    List<APIUser> users = new ArrayList<>();

    public MyListAdapter(List<APIUser> users){
        this.users = users;
    }

    @NonNull
    @Override
    public MyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = inflater.inflate(R.layout.user_lists, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.name.setText(users.get(i).getName());
        viewHolder.username.setText(users.get(i).getUsername());
        viewHolder.email.setText(users.get(i).getEmail());
        viewHolder.city.setText(users.get(i).getAddress().getCity());
        viewHolder.location.setText("Lat: "+users.get(i).getAddress().getGeo().getLat()+ " Lng: "+users.get(i).getAddress().getGeo().getLng());
        viewHolder.company.setText("Company: "+users.get(i).getCompany().getName());
        viewHolder.website.setText(users.get(i).getWebsite());
        viewHolder.street.setText(users.get(i).getAddress().getStreet());
        viewHolder.phone.setText(users.get(i).getPhone());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, username, email, street, city, location, phone, website, company;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            username = itemView.findViewById(R.id.username);
            email = itemView.findViewById(R.id.email);
            street = itemView.findViewById(R.id.street);
            city = itemView.findViewById(R.id.city);
            location = itemView.findViewById(R.id.location);
            phone = itemView.findViewById(R.id.phone);
            website = itemView.findViewById(R.id.website);
            company = itemView.findViewById(R.id.company);
        }
    }
}
