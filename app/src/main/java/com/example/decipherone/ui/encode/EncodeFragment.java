package com.example.decipherone.ui.encode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.decipherone.MainActivity;
import com.example.decipherone.R;
import com.example.decipherone.databinding.FragmentEncodeBinding;
import com.example.decipherone.ui.decode.DecodeFragment;
import com.example.decipherone.ui.encode.DashboardViewModel;

import org.w3c.dom.Text;

public class EncodeFragment extends Fragment {

    private FragmentEncodeBinding binding;
    int key = MainActivity.key;
    TextView messageInput;
    TextView messageOutput;
    public static String encodeData;
    char [] alphabet = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    char [] decodedData;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentEncodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messageInput = view.findViewById(R.id.encodeText);
        messageOutput = view.findViewById(R.id.encodeTextView2);
        Button encode = (Button) view.findViewById(R.id.encodeButton);
        Button clear = (Button) view.findViewById(R.id.clearEncodeButton);
        Button copy = (Button) view.findViewById(R.id.copyEncodeButton);
        encode.setOnClickListener(v -> {
            char [] data = messageInput.getText().toString().toCharArray();
            encodeData = String.valueOf(Encoder(data,key));
            messageOutput.setText(encodeData);
            messageInput.setText(null);
        });
        clear.setOnClickListener(v -> {
            messageInput.setText(null);
            messageOutput.setText(null);
        });
        copy.setOnClickListener(v -> {
            messageInput.setText(null);
            decodedData = DecodeFragment.decodeData.toCharArray();
            messageInput.setText(String.valueOf(decodedData));
            messageOutput.setText(String.valueOf(Encoder(decodedData,key)));
            encodeData = String.valueOf(Encoder(decodedData,key));
        });
    }

    public String Encoder(char [] string, int offset) {
        String encode = "";
        for (char letter : string) {
            for (int i = 0; i < alphabet.length; i++) {
                if (letter == alphabet[i]) {
                    int step = (i - offset) % 26;
                    if (step < 0) {
                        encode += alphabet[26 + step];
                        break;
                    }
                    encode += alphabet[step];
                    break;
                }
                else if (letter == ' ') {
                    encode += ' ';
                    break;
                }
                else if (letter == '!') {
                    encode += '!';
                    break;
                }
                else if (letter == '.') {
                    encode += '.';
                    break;
                }
                else if (letter == '\'') {
                    encode += '\'';
                    break;
                }
            }
        }
        return encode;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}