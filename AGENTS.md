# AGENTS.md

Ushbu fayl Codex va boshqa avtomatlashtirilgan agentlar uchun loyiha bo'yicha qisqa qo'llanma.

## Texnologiya va tuzilma
- Spring Boot 4.x, Maven
- PostgreSQL (asosiy DB), Redis (OTP rate-limit)
- JWT + OTP auth
- Swagger UI: `/swagger-ui/`, OpenAPI: `/v3/api-docs/`
- Asosiy modul/paketlar: user, asset, contract, claim, payment, admin, documents, evidence log, notifications, Telegram bot

## Lokal ishga tushirish
1) DB/Redis ish holatida bo'lishi kerak.
2) Konfiguratsiya `src/main/resources/application.yml` orqali boshqariladi.
3) Serverni ishga tushirish:
   - `.\mvnw spring-boot:run`

## Testlar
- Barcha testlar: `.\mvnw test`

## Xavfsizlik (permitAll)
Quyidagi endpointlar auth talab qilmaydi:
- `/api/v1/auth/**`
- `/api/v1/public/**`
- `/api/v1/payments/callback/**`
- `/api/payments/callback/**`
- `/swagger-ui/**`, `/v3/api-docs/**`, `/actuator/health`

## Kodga tegishli odatlar
- Controller endpoint yo'llarini o'zgartirishdan oldin security konfiguratsiyasini tekshiring.
- Payment callbacklarda `X-PAYMENT-SECRET` header talabi mavjud.
- Migratsiya yoki DB schema o'zgarishi bo'lsa, testlarni yangilash shart.

## O'zgarishlar bo'yicha minimal talablar
- Yangilangan endpointlar uchun Swagger annotatsiyalarini tekshiring.
- Auth va role-checklar (ADMIN) qat'iy saqlansin.
