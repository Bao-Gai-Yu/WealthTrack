package team.hiddenblue.wealthtrack.service;

import team.hiddenblue.wealthtrack.result.ExpenseRecordResult;

public interface TextInService {
    ExpenseRecordResult insertByTicket(byte[] img);

    ExpenseRecordResult insertByVoice(String sentence);

    ExpenseRecordResult insertByCommonImg(byte []img);
}
