package team.hiddenblue.wealthtrack.service;

import team.hiddenblue.wealthtrack.dto.ExpensesRecordResult;

public interface TextInService {
    ExpensesRecordResult insertByTicket(byte[] img);

    ExpensesRecordResult insertByVoice(String sentence);

    ExpensesRecordResult insertByCommonImg(byte []img);

    ExpensesRecordResult insertByReceipt(byte[] img);
}
