package backend.likelion.todos.goal;

import backend.likelion.todos.common.NotFoundException;
import backend.likelion.todos.member.Member;
import backend.likelion.todos.member.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final MemberRepository memberRepository;
    private final GoalRepository goalRepository;

    // 목표를 저장하고 저장된 목표의 ID를 반환합니다.
    public Long save(String name, String color, Long memberId) {
        // TODO [2단계] memberId를 사용하여 회원 정보를 조회하고, 없으면 "회원 정보가 없습니다." 메시지와 함께 NotFoundException을 발생시키세요.
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("회원 정보가 없습니다."));

        // TODO [2단계] Goal 인스턴스를 생성하세요.
        Goal goal = new Goal(name, color, member);

        // TODO [2단계] 생성된 Goal 인스턴스를 goalRepository에 저장하고, 저장된 목표의 ID를 반환하세요.
        goalRepository.save(goal);
        return goal.getId();
    }

    // 주어진 정보로 목표를 업데이트합니다.
    public void update(Long goalId, String name, String color, Long memberId) {
        // TODO [2단계] memberId를 사용하여 회원 정보를 조회하고, 없으면 "회원 정보가 없습니다." 메시지와 함께 NotFoundException을 발생시키세요.
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("회원 정보가 없습니다."));

        // TODO [2단계] goalId를 사용하여 목표 정보를 조회하고, 없으면 "목표 정보가 없습니다." 메시지와 함께 NotFoundException을 발생시키세요.
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new NotFoundException("목표 정보가 없습니다."));

        // TODO [2단계] 조회한 목표가 주어진 회원의 것인지 검증하세요.
        goal.validateMember(member);

        // TODO [2단계] 목표의 name과 color를 업데이트하세요.
        goal.update(name, color);
    }

    // 목표를 삭제합니다.
    public void delete(Long goalId, Long memberId) {
        // TODO [2단계] memberId를 사용하여 회원 정보를 조회하고, 없으면 "회원 정보가 없습니다." 메시지와 함께 NotFoundException을 발생시키세요.
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("회원 정보가 없습니다."));

        // TODO [2단계] goalId를 사용하여 목표 정보를 조회하고, 없으면 "목표 정보가 없습니다." 메시지와 함께 NotFoundException을 발생시키세요.
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new NotFoundException("목표 정보가 없습니다."));

        // TODO [2단계] 조회한 목표가 주어진 회원의 것인지 검증하세요.
        goal.validateMember(member);

        // TODO [2단계] goalRepository에서 해당 목표를 삭제하세요.
        goalRepository.delete(goal);
    }

    // 특정 회원 ID에 속하는 모든 목표를 조회하여 GoalResponse 리스트로 반환합니다.
    public List<GoalResponse> findAllByMemberId(Long memberId) {
        // TODO [2단계] memberId를 사용하여 모든 목표를 조회하세요.
        List<Goal> goals = goalRepository.findAllByMemberId(memberId);

        // TODO [2단계] 조회된 목표 리스트를 GoalResponse 리스트로 변환하여 반환하세요.
        return goals.stream()
                .map(goal -> new GoalResponse(goal.getId(), goal.getName(), goal.getColor()))
                .collect(Collectors.toList());
    }
}
