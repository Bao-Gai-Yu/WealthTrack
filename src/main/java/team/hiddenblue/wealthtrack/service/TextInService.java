package team.hiddenblue.wealthtrack.service;

import team.hiddenblue.wealthtrack.Result.ExpenseRecordResult;

public interface TextInService {
    ExpenseRecordResult insertByTicket(byte[] img);

    ExpenseRecordResult insertByVoice(String sentence);
}
