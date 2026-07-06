package ir.library.service;

import ir.library.exception.DatabaseRepositoryException;
import ir.library.exception.DuplicateMemberException;
import ir.library.exception.MemberNotFoundException;
import ir.library.model.Member;
import ir.library.repository.MemberRepository;

public class MemberService {
    private MemberRepository memberRepository;

    public MemberService() {
        this.memberRepository = new MemberRepository();
    }

    public Long registerMember(String fullName, String phoneNumber) throws DatabaseRepositoryException {
        if (fullName == null || fullName.isBlank() || fullName.length() > 100)
            throw new IllegalArgumentException("Full Name Cannot be Null or Empty and The Length Must Less Than 100!");

        if (phoneNumber == null || phoneNumber.isBlank() || phoneNumber.length() > 15)
            throw new IllegalArgumentException("Phone Number Cannot be Null or Empty and The Length Must Less Than 15!");

        if (memberRepository.existByPhoneNumber(phoneNumber))
            throw new DuplicateMemberException("Phone Number Already Exist!");

        return memberRepository.insert(new Member(fullName, phoneNumber));
    }

    public Member getMemberById(Long id) throws DatabaseRepositoryException {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("ID Cannot be Null or Less Than 1!");

//        Optional<Member> optionalMember = memberRepository.findById(id);
//        if (optionalMember.isEmpty()) throw new MemberNotFoundException("Member Not Found!");
//        return optionalMember.get();

        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member Not Found!"));
    }

    public Member changeMemberFullName(Long id, String newFullName) throws DatabaseRepositoryException {
        if (id == null || id <= 0)
            throw new IllegalArgumentException("ID Cannot be Null or Less Than 1!");

        if (newFullName == null || newFullName.isBlank() || newFullName.length() > 100)
            throw new IllegalArgumentException("New Full Name Cannot be Null or Empty and The Length Must Less Than 100!");

        if (!memberRepository.updateFullName(id, newFullName))
            throw new MemberNotFoundException("Member Not Found!");

        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member Not Found!"));
    }
}
