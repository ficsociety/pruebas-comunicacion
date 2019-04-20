using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ButtonHandler : MonoBehaviour {

	public void BackToAndroid()
    {
        // Crear referencia a la clase contenedora
        AndroidJavaClass fragmentClass = new AndroidJavaClass("apm.muei.bs.UnityFragment");
        // Obtener instancia del fragmento contenedor
        AndroidJavaObject fragment = fragmentClass.GetStatic<AndroidJavaObject>("instance");
        // Llamar método de backToAndroid
        fragment.Call("onButtonPressed");
    }
}
