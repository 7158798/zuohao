package test.com.ruizton.main.controller.front;

import com.ruizton.main.aspect.SysLog;
import com.ruizton.main.controller.front.FquestionValidateJsonController;
import com.ruizton.main.log.LOG;
import com.ruizton.main.model.Fquestionvalidate;
import com.ruizton.util.JsonHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import test.com.ruizton.base.BaseSpringTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luwei on 17-3-28.
 */
public class FquestionValidateJsonControllerTest extends BaseSpringTest {


    @Autowired
    private FquestionValidateJsonController fquestionValidateJsonController;

   /* @Test
    public void getAllQuestionValidate() {
        String str = fquestionValidateJsonController.getAllQuestionValidate();
        LOG.i(this, str);
    }*/

    @Test
    public void saveQuest() {
        Fquestionvalidate quest1 = new Fquestionvalidate();
        quest1.setFquestion("你的妈妈叫什么名字？");
        quest1.setFanswer("A");
        Fquestionvalidate quest2 = new Fquestionvalidate();
        quest2.setFquestion("你的爸爸叫什么名字？");
        quest2.setFanswer("B");
        List<Fquestionvalidate> list = new ArrayList<>();
        list.add(quest1);
        list.add(quest2);
        String jsonstr = JsonHelper.obj2JsonStr(list);
        System.out.println(jsonstr);
        String str = fquestionValidateJsonController.saveQuestion(jsonstr);
        LOG.i(this, str);
    }


    @Override
    public void doTest() {

    }
}
