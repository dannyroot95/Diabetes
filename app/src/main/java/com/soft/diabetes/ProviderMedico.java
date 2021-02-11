package com.soft.diabetes;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProviderMedico {
    DatabaseReference mDatabaseReference;

    public ProviderMedico(){
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Medico");
    }


    public Task<Void> MappingAData(UserMedico user) {
        Map<String , Object> map = new HashMap<>();
        map.put("id",user.getId());
        map.put("nombres", user.getNombre());
        map.put("apellidos", user.getApellido());
        map.put("dni", user.getDni());
        map.put("email", user.getEmail());
        return mDatabaseReference.child(user.getId()).setValue(map);
    }

    public DatabaseReference getData(String id){
        return mDatabaseReference.child(id);
    }

}
