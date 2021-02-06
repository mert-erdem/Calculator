package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    TextView textView;
    Button button;

    String sayi1="", sayi2="", islem;
    Double sonuc;
    Double temp;//işaret değiştirmede kullanılacak olan bir değişken

    boolean islemeTiklandi=false;//birden fazla işlem yapılmasına engel olan bir kontrol


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.textView);
    }



    //Rakam butonları
    public void bClickNumber(View view)
    {
        button=(Button)view;
        textView.setText(textView.getText()+""+button.getText());

        if(islemeTiklandi==true)//işlem sembolünden sonra sayi2 dolduruluyor;
        {
            sayi2+=button.getText();
        }
        else
        {
            sayi1+=button.getText();
        }
    }

    //Clear butonu
    public void bClickClear(View view)
    {
        Clear();
    }

    //değerleri temizlemek için bir metod;
    private void Clear()
    {
        //bellekteki sayılar da dahil textView ın string i temizlendi;
        textView.setText("");
        sayi1="";
        sayi2="";
        sonuc=null;
        islem="";
        islemeTiklandi=false;
    }

    //= butonu;
    public void bClickResult(View view)
    {
        try
        {
            switch (islem)
            {
                case "+":
                    sonuc=Double.parseDouble(sayi1)+Double.parseDouble(sayi2);
                    break;
                case "-":
                    sonuc=Double.parseDouble(sayi1)-Double.parseDouble(sayi2);
                    break;
                case "*":
                    sonuc=Double.parseDouble(sayi1)*Double.parseDouble(sayi2);
                    break;
                case "/":
                    sonuc=Double.parseDouble(sayi1)/Double.parseDouble(sayi2);
                    break;
            }
        }
        catch (Exception e)
        {
            //fazladan işlem olmaması için(toast, Clear() diğer durumlarda da kullanılacak) boş bırakıldı;
        }


        if(sonuc!=null && !sonuc.isNaN() && !sonuc.isInfinite())//işlem sorunsuz ise
        {
            textView.setText(textView.getText()+"="+sonuc);
        }
        else//kesin olarak bir sorun var(Exception || NaN || Infinite)
        {
            Clear();
            Toast.makeText(getApplicationContext(),"Geçersiz işlem",Toast.LENGTH_SHORT).show();
        }
        islemeTiklandi=false;
    }

    //İşlem butonları
    public void bClickOperator(View view)
    {
        button = (Button) view;

        if(islemeTiklandi==false && !sayi1.isEmpty())//sayi1 kontrolü, başta herhangi bir operatöre basılamaması, işlemeTiklandi ise birden fazla operatörü engellemek içindir.
        {
            textView.setText(textView.getText() + "" + button.getText());
            islemeTiklandi = true;
            islem = button.getText().toString();

            if (sonuc != null)
            {
                //Eğer işlem yapıldıktan sonra çıkan sonuç ile tekrar bir işlem yapılması gerekiyorsa;
                sayi1 = sonuc.toString();
                textView.setText(sayi1+button.getText());
                sayi2 = "";
                sonuc = null;
            }
        }
        else if(islemeTiklandi==true && !sayi1.isEmpty() && sayi2.isEmpty())//işleme tıklandı fakat işlem başka bir işlem ile değiştirilmek isteniyor ise
        {
            islem=button.getText().toString();
            textView.setText(sayi1+islem);
        }
    }


    //İşaret değiştirme butonu; sayıyı tuşladıktan sonra basılıyor
    public void bClickArtiEksi(View view)
    {
        button=(Button) view;

        if(sonuc==null)//= butonuna basılmamış ise;
        {
            if(!sayi2.isEmpty())
            {
                temp=Double.parseDouble(sayi2)*(-1);
                sayi2=temp.toString();
                textView.setText(sayi1+" "+islem+" ("+sayi2+")");
            }
            else if((!sayi1.isEmpty()))
            {
                temp=Double.parseDouble(sayi1)*(-1);
                sayi1=temp.toString();

                if(islemeTiklandi)
                {
                    textView.setText(sayi1+islem);
                }
                else
                {
                    textView.setText(sayi1);
                }
            }
        }
        else//eğer sonuc hesaplanmış ise; bir sonraki işlemde sonucun ters işaretlesi ile devam edilmek istenirse;
        {
            sonuc=sonuc*(-1);
            textView.setText(sonuc+"");
        }
    }
}