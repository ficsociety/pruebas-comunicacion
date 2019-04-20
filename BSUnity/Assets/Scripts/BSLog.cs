using UnityEngine;
using UnityEngine.UI;

public class BSLog : MonoBehaviour {

    private AndroidJavaObject currentActivity;
    public Text _text;

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}

    void SetText(string textFromAndroid)
    {
        _text.text = textFromAndroid;
    }
}
