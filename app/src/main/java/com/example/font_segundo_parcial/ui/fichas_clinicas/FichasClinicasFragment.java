package com.example.font_segundo_parcial.ui.fichas_clinicas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.font_segundo_parcial.R;
import com.example.font_segundo_parcial.api.AdapterFichaClinica;
import com.example.font_segundo_parcial.api.Datos;
import com.example.font_segundo_parcial.api.FichaClinica;
import com.example.font_segundo_parcial.api.RetrofitUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FichasClinicasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FichasClinicasFragment extends Fragment {

    private RecyclerView rvFichasClinicas;  // Para la lista de fichas clínicas
    private AdapterFichaClinica adapterFichaClinica;

    FichaClinica[] fichasClinicas;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FichasClinicasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FichasClinicasFragment.
     */
    public static FichasClinicasFragment newInstance(String param1, String param2) {
        FichasClinicasFragment fragment = new FichasClinicasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_fichas_clinicas, container, false);

        // RecyclerView para la lista de fichas clínicas
        rvFichasClinicas = vista.findViewById(R.id.listaFichasClinicas);
        rvFichasClinicas.setLayoutManager(new LinearLayoutManager(getContext()));

        // cargar los datos
        cargarFichasClinicas();

        // Inflate the layout for this fragment
        return vista;
    }

    /**
     * Traer del back las fichas clíncas
     */
    public void cargarFichasClinicas() {
        Call<Datos<FichaClinica>> callApi = RetrofitUtil.getFichaClinicaService()
                .obtenerFichasClinicas();
        callApi.enqueue(new Callback<Datos<FichaClinica>>() {
            @Override
            public void onResponse(Call<Datos<FichaClinica>> call, Response<Datos<FichaClinica>> response) {

                fichasClinicas = response.body().getLista();
                // poner en el RecyclerView
                adapterFichaClinica = new AdapterFichaClinica(fichasClinicas);
                rvFichasClinicas.setAdapter(adapterFichaClinica);

                // log
                Log.i("s","imprimiendo fichas clínicas");
                for (FichaClinica ficha: fichasClinicas)
                {
                    Log.i("s", ficha.getFechaHora());
                }

            }

            @Override
            public void onFailure(Call<Datos<FichaClinica>> call, Throwable t) {
                Log.e("s", t.toString());
            }
        });
    }
}