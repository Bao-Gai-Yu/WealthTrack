package team.hiddenblue.wealthtrack.service;

import team.hiddenblue.wealthtrack.dto.ExpenseRecordResult;

public interface TextInService {
    ExpenseRecordResult insertByTicket(byte[] img);

    ExpenseRecordResult insertByVoice(String sentence);

    ExpenseRecordResult insertByCommonImg(byte []img);
}
