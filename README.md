# Code

재현의 프로젝트 코드 모음. 각 프로젝트는 서브모듈로 연결되어 있다.

```bash
git clone --recurse-submodules https://github.com/GgaGGa0302/Code.git
# 또는 이미 받은 뒤
git submodule update --init --recursive
```

## 프로젝트

| 프로젝트 | 저장소 | 설명 |
|---|---|---|
| zoop | [Donbug/zoop](https://github.com/Donbug/zoop) | 돈벌래 — 뉴스·공시 기반 기업 관계 탐색 서비스 (모노레포: Web / API / AI) |
| zoop-infra | [Donbug/zoop-infra](https://github.com/Donbug/zoop-infra) | 돈벌래 인프라 — Terraform, 배포 스크립트, 클라우드 문서 |
| fin-account-hub | [GgaGGa0302/fin-account-hub](https://github.com/GgaGGa0302/fin-account-hub) | 스마트뱅크 — MSA 기반 디지털 계좌 통합 관리 플랫폼 |
| LG-CNS-AM-INSPIRE_5_FE | [GgaGGa0302/LG-CNS-AM-INSPIRE_5_FE](https://github.com/GgaGGa0302/LG-CNS-AM-INSPIRE_5_FE) | 모아패밀리 — LG CNS AM Inspire Camp 5기 (FE) |
| LG-CNS-AM-INSPIRE_5_BE | [GgaGGa0302/LG-CNS-AM-INSPIRE_5_BE](https://github.com/GgaGGa0302/LG-CNS-AM-INSPIRE_5_BE) | LG CNS AM Inspire Camp 5기 (BE) |
| Project-Nova | [GgaGGa0302/Project-Nova](https://github.com/GgaGGa0302/Project-Nova) | Unreal Engine 기반 던전 RPG (캡스톤) |
| boorov | [GgaGGa0302/boorov](https://github.com/GgaGGa0302/boorov) | 2D C++ 슈팅게임 |
| gp-assignment | [GgaGGa0302/gp-assignment](https://github.com/GgaGGa0302/gp-assignment) | 게임프로그래밍 과제 |
| Potgame | 서브모듈 (원본 저장소 미확인) | Potgame |

## 서브모듈 추가하기

```bash
git submodule add https://github.com/Donbug/zoop.git zoop
git submodule add https://github.com/Donbug/zoop-infra.git zoop-infra
git commit -m "Add zoop and zoop-infra submodules"
git push
```
