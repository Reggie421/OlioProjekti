package com.example.sdidebartesti;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    Button logout;
    Button deleteAcc;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logout = (Button) view.findViewById(R.id.logout);
        deleteAcc = (Button) view.findViewById(R.id.deleteaccount);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class); //launches loginActivity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);//clears backspace history so you can't go back into settings after logging out
                startActivity(intent);
            }
        });

        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountManager am = AccountManager.getInstance();
                am.deleteAccount(MainActivity.getmInstanceActivity().getAccountName());
                Intent intent = new Intent(getActivity(), LoginActivity.class);//launches loginActivity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);//clears backspace history so you can't go back into settings after deleting account
                startActivity(intent);
            }
        });

    }
}
