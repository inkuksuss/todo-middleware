package com.project.todo.domain.types;

public enum LOGIN_PROVIDER {
    TODO("todo"),
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao");

    private final String providerValue;

    LOGIN_PROVIDER(String providerValue) {
        this.providerValue = providerValue;
    }

    public String getProviderValue() {
        return providerValue;
    }
}
