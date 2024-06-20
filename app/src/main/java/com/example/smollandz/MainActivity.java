package com.example.smollandz;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String[] formats = {"OMO 300G", "OMO 580G", "OMO 800G", "OMO 1.4KG"};
    private boolean[] formatsChecked = new boolean[formats.length];
    private String[] criteresAchat = {"Prix et promotions", "Durabilité du parfum", "Soin des couleurs", "Efficacité contre les tâches", "Protection de la peau sensible"};
    private boolean[] criteresAchatChecked = new boolean[criteresAchat.length];

    private String[] items = {"OMO", "BINGO", "LECHAT", "TEST", "ISIS", "SURF", "Autre"};
    private boolean[] checkedItems = new boolean[items.length];
    private Spinner spinnerMarquePrefere;

    private RadioButton radioButtonOui;
    private RadioButton radioButtonNon;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioButtonOui = findViewById(R.id.radioButton_oui);
        radioButtonNon = findViewById(R.id.radioButton_non);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButton_oui) {
                showFormatDialog();
                showCriteresAchatDialog();
                showEvaluationDialog();
            }
        });



        Button showDialogButton = findViewById(R.id.button_show_dialog);
        showDialogButton.setOnClickListener(v -> showMultiChoiceDialog());

        spinnerMarquePrefere = findViewById(R.id.spinnerSuperviseur);

        ArrayAdapter<CharSequence> adapterSuperviseur = ArrayAdapter.createFromResource(this,
                R.array.noms_fr_achat_hs, R.layout.spinner_item);
        adapterSuperviseur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarquePrefere.setAdapter(adapterSuperviseur);
    }

    private void showMultiChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Options")
                .setMultiChoiceItems(items, checkedItems, (dialog, which, isChecked) -> checkedItems[which] = isChecked)
                .setPositiveButton("OK", (dialog, which) -> {
                    StringBuilder selectedItems = new StringBuilder();
                    for (int i = 0; i < checkedItems.length; i++) {
                        if (checkedItems[i]) {
                            if (selectedItems.length() > 0) {
                                selectedItems.append(", ");
                            }
                            selectedItems.append(items[i]);
                        }
                    }
                    Toast.makeText(MainActivity.this, "Selected: " + selectedItems.toString(), Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showFormatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quelle est le format que vous avez l'habitude d'acheter ?")
                .setMultiChoiceItems(formats, formatsChecked, (dialog, which, isChecked) -> formatsChecked[which] = isChecked)
                .setPositiveButton("OK", (dialog, which) -> {
                    StringBuilder selectedFormats = new StringBuilder();
                    for (int i = 0; i < formatsChecked.length; i++) {
                        if (formatsChecked[i]) {
                            if (selectedFormats.length() > 0) {
                                selectedFormats.append(", ");
                            }
                            selectedFormats.append(formats[i]);
                        }
                    }
                    Toast.makeText(MainActivity.this, "Selected: " + selectedFormats.toString(), Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showCriteresAchatDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quel est votre principal critère d'achat de ce produit OMO ?")
                .setMultiChoiceItems(criteresAchat, criteresAchatChecked, (dialog, which, isChecked) -> criteresAchatChecked[which] = isChecked)
                .setPositiveButton("OK", (dialog, which) -> {
                    StringBuilder selectedCriteres = new StringBuilder();
                    for (int i = 0; i < criteresAchatChecked.length; i++) {
                        if (criteresAchatChecked[i]) {
                            if (selectedCriteres.length() > 0) {
                                selectedCriteres.append(", ");
                            }
                            selectedCriteres.append(criteresAchat[i]);
                        }
                    }
                    Toast.makeText(MainActivity.this, "Selected: " + selectedCriteres.toString(), Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showEvaluationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Évaluation du produit OMO");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String evaluation = input.getText().toString();
            int eval = Integer.parseInt(evaluation);
            if (eval >= 1 && eval <= 10) {
                Toast.makeText(MainActivity.this, "Évaluation : " + eval, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                showCriteresAchatDialog();
            } else {
                Toast.makeText(MainActivity.this, "Veuillez saisir un nombre entre 1 et 10", Toast.LENGTH_SHORT).show();
                showEvaluationDialog();
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
