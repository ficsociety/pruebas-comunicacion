using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class AndroidCommunication : MonoBehaviour {

    public void CallAndroid() {
        // Crear referencia a la clase contenedora
        AndroidJavaClass fragmentClass = new AndroidJavaClass("apm.muei.bs.UnityFragment");
        // Obtener instancia del fragmento contenedor
        AndroidJavaObject fragment = fragmentClass.GetStatic<AndroidJavaObject>("instance");
        // Llamar método de comunicación
        fragment.Call("onAndroidUnityCommunication");
    }

    // Método al que Android llamará
    public void ShowText(string text)
    {
        Text txt = transform.Find("AssociatedText").GetComponent<Text>();
        txt.text = text;
    }
}
