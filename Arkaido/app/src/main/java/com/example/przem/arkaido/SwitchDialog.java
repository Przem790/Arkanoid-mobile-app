package com.example.przem.arkaido;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

/**
 * Created by przem on 09.05.2017.
 */

public class SwitchDialog extends DialogFragment {
    Arkaido.ArkaidoView obj;

    public SwitchDialog(){

    }

    public void onStart(){
        super.onStart();
        Dialog dl = SwitchDialog.this.getDialog();
        Button ok = (Button)dl.findViewById(R.id.button);
        final Switch switchh = (Switch)dl.findViewById(R.id.switch1);
        if(obj.isgravityenabled)
        switchh.setChecked(true);
        else
            switchh.setChecked(false);
        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SwitchDialog.this.dismiss();
            }
        });

        if (switchh != null) {
            switchh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        obj.isgravityenabled=true;
                    } else {
                        obj.isgravityenabled=false;
                    }
                }
            });
        }

    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceBundle){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialoglayout,null));
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            this.obj=(Arkaido.ArkaidoView)bundle.getSerializable("object");
        }
        return builder.create();

    }
}
