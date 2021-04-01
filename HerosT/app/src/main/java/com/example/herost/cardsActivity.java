package com.example.herost;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class cardsActivity extends AppCompatActivity {

    private ArrayList<String> al;
    private ArrayAdapter<String> arrayAdapter;
    private int i;
    private ArrayList<Integer> answers;

    int images[]={R.drawable.klaf_image,R.drawable.amsterdam2,R.drawable.solider3,
            R.drawable.diary4,R.drawable.train5,R.drawable.dead6};
    int j =0;
    Button endGamebtn;

    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        answers = new ArrayList<>();
        al = new ArrayList<>();
        setContentView(R.layout.activity_cards);
        setAnnaStory();

        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.helloText, al );
        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                al.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

                answers.add(2);
                Toast.makeText(cardsActivity.this,"נבחר תשובה 2",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {

                answers.add(1);
                Toast.makeText(cardsActivity.this,"נבחר תשובה 1",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
//                al.add("fin".concat(String.valueOf(i)));
//                arrayAdapter.notifyDataSetChanged();
//                Log.d("LIST", "notified");
//                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(cardsActivity.this,"click",Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.endLvlBtn).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(cardsActivity.this, EndGameActivity.class);
                startActivity(intent);
            }
        });

    }

    private void  setAnnaStory(){
        String line1 = "היי קוראים לי אנה, ";
        String line2 = "בדיוק עברנו לאמסטרדם";
        String line3 = "ואבא פתח עסק שנראה ממש חשוב";
        String line4 = "והכי חשוב שאני פוגשת";
        String line5 = "מלא חברות חדשות.";
        al.add(line1 +"\n"+ line2 +"\n"+ line3 +"\n"+line4+"\n\n"+line5);
         line1 = "כל כך נהנו מהחיים החדשים בהולנד";
         line2 = "ואז הנאצים פלשו!";
         line3 = "ההורים מתלבטים מה לעשות עכשיו";
         line4 = "1) להשאר בהולנד בתקווה שהמלחמה תעבור מהר";
         line5 = "2) לברוח למקום בטוח יותר ";
        al.add(line1 +"\n"+ line2 +"\n"+ line3 +"\n\n"+line4+"\n\n"+line5);

        line1 = "הגרמנים אוסרים עלינו ללכת למקומות הבילוי האהובים עליי";
        line2 = "אסור בתי קפה, קולנוע, \n אפילו לא מרשים לי להפגש אם החברות החדשות שלי";
        al.add(line1 +"\n"+ line2 +"\n");

        line2 = "והכי גרוע! לקחו את אחותי ל''מחנה עבודה'' ועכשיו גם רוצים שאני אלך";
        line3 = "אני לא יודעת מה לעשות??";
        line4 = "1) למצוא דרך להתחמק מהמחנה עבודה ";
        line5 = "2) ללכת למחנה עבודה בתקווה שהכל יעבור בשלום ";
        al.add( line2 +"\n"+ line3 +"\n\n"+line4+"\n\n"+line5);

        line1 = "לאחר שהצלחנו להתחבא מהגרמנים כל כך הרבה זמן";
        line2 = "פתאום אנחנו שומרים דפיקות חזקות בדלת";
        line3 = "זה האס אס! הם באו לקחת אותנו";
        al.add( line1 +"\n"+ line2 +"\n\n"+line3);

        line1 ="אבל מי גילה אותנו? היינו כל כך שקטים";
        line2 = "1) עד היום , שאלה זו ללא תשובה";
        line3 = "2) אחד החברים של אבא נכנע ללחץ כדי להציל את משפחתו";
        al.add( line1  +"\n\n"+line2+"\n\n"+line3);

        line1 ="הגרמנים אמרו לנו לעלות לרכבת שאמורה לקחת שלושה ימים";
        line2 = "אך הם לא אמרו לנו לאן לוקחים אותנו";
        line3 = "שמו אותנו בקרון עם המון אנשים ולא היה לנו מקום לנשום";
        al.add( line1  +"\n"+line2+"\n"+line3);

        line1 ="שמענו שמועות על המקומות שאליהן מגיעה הרכבת.";
        line2 = "מעניין לאן לוקחים אותנו?";
        line3 = "1) מחנה העבודה אוושויץ";
        line4 = "2) מחנה העבודה טרבלינקה שם מחכים כבר משפחתו של אחד הנוסעים";
        al.add( line1  +"\n"+line2+"\n"+line3+"\n"+line4);

        line1 ="הגענו לאוושיץ, וזה המקום הכי גרוע שהייתי בוא";
        line2 = "ואז ביום אחד הגרמנים החליטו שהם מעבירים אותי ואת אחותי";
        line3 = "ואותי ואת אחותי הם הפרידו מההורים";
        al.add( line1  +"\n"+line2+"\n"+line3);

        line1 = "אחותי שאלה אותי, אנה, לאן הולכים?";
        line2 = "אני עניתי לה:";
        line3 = "1) נוסעים למחנה חדש, קוראים לו ברגן-בלזן";
        line4 = "2) לא שמעת? המלחמה כמעט נגמרת לוקחים אותנו למקום יותר טוב";
        al.add( line1  +"\n\n"+line2+"\n\n"+line3+"\n"+line4);

        line1 = "אנה ואחותה הגיעו למחנה ברגן-בלזן";
        line2 = "שם אנה מתה ממחלה בשם ''טיפוס''";
        line3 = "את סיפורה אביה סיפר מתוך היומן שאנה רשמה כל המלמה";
        line4 = "יהי זיכרה ברוך";
        al.add( line1  +"\n"+line2+"\n\n"+line3+"\n\n"+line4);

    }
}