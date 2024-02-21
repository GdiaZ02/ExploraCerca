package com.example.exploracerca.data

import com.example.exploracerca.R
import com.example.exploracerca.modelo.Restaurantes

object RestaurantesData {

    val defaultRestaurante = getRestauranteData()[0]


    fun getRestauranteData(): List<Restaurantes> {

        return listOf(
            Restaurantes(
                id = 1,
                titleResourceId = R.string.DiverXO,
                imageResourceId = R.drawable.diverxoimg,
                restauranteDetails = R.string.DiverXO_D,
                Capacidad = 15,
                latitud=40.4558,
                longitud=-3.6903
            ),
            Restaurantes(
                id = 2,
                titleResourceId = R.string.Coque,
                imageResourceId = R.drawable.coqueimg,
                restauranteDetails = R.string.Coque_D,
                Capacidad = 25,
                latitud=40.4479,
                longitud=-3.7131
            ),
            Restaurantes(
                id = 3,
                titleResourceId = R.string.Santceloni,
                imageResourceId = R.drawable.santceloniimg,
                restauranteDetails = R.string.Santceloni_D,
                Capacidad = 25,
                latitud=40.4627,
                longitud=-3.6886
            ),
            Restaurantes(
                id = 4,
                titleResourceId = R.string.DSTAgE,
                imageResourceId = R.drawable.dstageimg,
                restauranteDetails = R.string.DSTAgE_D,
                Capacidad = 15,
                latitud=40.4223,
                longitud=-3.6988
            ),
            Restaurantes(
                id = 5,
                titleResourceId = R.string.Ramón_Freixa_Madrid,
                imageResourceId = R.drawable.diverxoimg,

                restauranteDetails = R.string.Ramón_Freixa_Madrid_D,
                Capacidad = 25,
                latitud=40.4316,
                longitud=-3.6882
            ),
            Restaurantes(
                id = 6,
                titleResourceId = R.string.La_Terraza_del_Casino,
                imageResourceId = R.drawable.la_terraza_del_casinoimg,
                restauranteDetails = R.string.La_Terraza_del_Casino_D,
                Capacidad = 25,
                latitud=40.4179,
                longitud=-3.6988
            ),Restaurantes(
                id = 7,
                titleResourceId = R.string.Álbora,
                imageResourceId = R.drawable._lboraimg,
                restauranteDetails = R.string.Álbora_D,
                Capacidad = 20,
                latitud=40.4264,
                longitud=-3.6914
            ),Restaurantes(
                id = 8,
                titleResourceId = R.string.Sacha,
                imageResourceId = R.drawable.sachaimg,
                restauranteDetails = R.string.Sacha_D,
                Capacidad = 15,
                latitud=40.4247,
                longitud=-3.6882
            ),Restaurantes(
                id = 9,
                titleResourceId = R.string.Punto_MX,
                imageResourceId = R.drawable.diverxoimg,
                restauranteDetails = R.string.Punto_MX_D,
                Capacidad = 20,
                latitud=40.4229,
                longitud=-3.6991
            ),Restaurantes(
                id = 10,
                titleResourceId = R.string.Ten_Con_Ten,
                imageResourceId = R.drawable.diverxoimg,
                restauranteDetails = R.string.Ten_Con_Ten_D,
                Capacidad = 20,
                latitud=40.4268,
                longitud=-3.6889
            ),
            Restaurantes(
                id = 11,
                titleResourceId = R.string.Kabuki_Wellington,
                imageResourceId = R.drawable.kabuki_wellingtonimg,
                restauranteDetails = R.string.Kabuki_Wellington_D,
                Capacidad = 25,
                latitud=40.4222,
                longitud=-3.6871
            ),
            Restaurantes(
                id = 12,
                titleResourceId = R.string.El_Club_Allard,
                imageResourceId = R.drawable.el_club_allardimg,
                restauranteDetails = R.string.El_Club_Allard_D,
                Capacidad = 15,
                latitud=40.4148,
                longitud=-3.6984
            ),
            Restaurantes(
                id = 13,
                titleResourceId = R.string.El_Celler_de_Can_Roca,
                imageResourceId = R.drawable.el_celler_de_can_rocaimg,
                restauranteDetails = R.string.El_Celler_de_Can_Roca_D,
                Capacidad = 30,
                latitud=41.9817,
                longitud=2.8075
            ),
            Restaurantes(
                id = 14,
                titleResourceId = R.string.StreetXO,
                imageResourceId = R.drawable.streetxoimg,
                restauranteDetails = R.string.StreetXO_D,
                Capacidad = 20,
                latitud=40.4192,
                longitud=-3.6931
            ),
            Restaurantes(
                id = 15,
                titleResourceId = R.string.ABarra,
                imageResourceId = R.drawable.abarraimg,
                restauranteDetails = R.string.ABarra_D,
                Capacidad = 20,
                latitud=40.4275,
                longitud=-3.6855
            ),
            Restaurantes(
                id = 16,
                titleResourceId = R.string.Gaytán,
                imageResourceId = R.drawable.gayt_nimg,
                restauranteDetails = R.string.Gaytán_D,
                Capacidad = 25,
                latitud=40.4304,
                longitud=-3.7060
            ),
            Restaurantes(
                id = 17,
                titleResourceId = R.string.El_Paraguas,
                imageResourceId = R.drawable.el_paraguasimg,
                restauranteDetails = R.string.El_Paraguas_D,
                Capacidad = 25,
                latitud=40.4270,
                longitud=-3.6886
            ),
            Restaurantes(
                id = 18,
                titleResourceId = R.string.La_Tasquita_de_Enfrente,
                imageResourceId = R.drawable.la_tasquita_de_enfrenteimg,
                restauranteDetails = R.string.La_Tasquita_de_Enfrente_D,
                Capacidad = 30,
                latitud=40.4233,
                longitud=-3.6994
            ),
            Restaurantes(
                id = 19,
                titleResourceId = R.string.El_Qüenco_de_Pepa,
                imageResourceId = R.drawable.el_q_enco_de_pepaimg,
                restauranteDetails = R.string.El_Qüenco_de_Pepa_D,
                Capacidad = 25,
                latitud=40.4225,
                longitud=-3.7033
            ),
            Restaurantes(
                id = 20,
                titleResourceId = R.string.Viridiana,
                imageResourceId = R.drawable.viridianaimg,
                restauranteDetails = R.string.Viridiana_D,
                Capacidad = 20,
                latitud=40.4214,
                longitud=-3.6979
            )

        )
    }
}
