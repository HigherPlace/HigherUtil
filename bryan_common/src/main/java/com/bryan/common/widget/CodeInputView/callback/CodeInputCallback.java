package com.bryan.common.widget.CodeInputView.callback;

/**
 * T传入的目的是为了更方便的控制CodeInput输入，如校验这个字母符不符合格式，不符合就删了。暂时没需求就没写这些方法
 */
public interface CodeInputCallback<T> {

    void onInputFinish(T ci, String inputResult);
    void onInput(T ci, Character currentChar);

}
