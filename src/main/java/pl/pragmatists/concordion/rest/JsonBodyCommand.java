package pl.pragmatists.concordion.rest;

import org.concordion.api.AbstractCommand;
import org.concordion.api.CommandCall;
import org.concordion.api.Element;
import org.concordion.api.Evaluator;
import org.concordion.api.ResultRecorder;
import org.concordion.api.listener.AssertEqualsListener;
import org.concordion.internal.listener.AssertResultRenderer;
import org.concordion.internal.util.Announcer;

import pl.pragmatists.concordion.rest.util.JsonPrettyPrinter;

public class JsonBodyCommand extends AbstractCommand {

    private Announcer<AssertEqualsListener> listeners = Announcer.to(AssertEqualsListener.class);
        
    public JsonBodyCommand() {
        listeners.addListener(new AssertResultRenderer());
    }
    
    @Override
    public void setUp(CommandCall commandCall, Evaluator evaluator, ResultRecorder resultRecorder) {
        
        Element element = commandCall.getElement();
        element.addStyleClass("json");        
        
        String body = new JsonPrettyPrinter().prettyPrint(element.getText());
        element.moveChildrenTo(new Element("tmp"));
        element.appendText(body);
        
        RequestExecutor request = RequestExecutor.fromEvaluator(evaluator);
        request.body(body);
    }
    
}
