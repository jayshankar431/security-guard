package com.example.securityguard

// State Management for Fake Call Service
enum class CallState {
    RINGING,
    ACTIVE,
    DISCONNECTED
}

class FakeCallService {
    private var state: CallState = CallState.DISCONNECTED
    private var callTimer: Timer? = null

    fun initiateCall() {
        if (state == CallState.DISCONNECTED) {
            state = CallState.RINGING
            println("Call is ringing...")
            startMissedCallTimer()
        }
    }

    fun acceptCall() {
        if (state == CallState.RINGING) {
            state = CallState.ACTIVE
            println("Call is now active...")
            startActiveCallTimer()
            callTimer?.cancel() // Cancel missed call timer
        }
    }

    fun disconnectCall() {
        state = CallState.DISCONNECTED
        println("Call has been disconnected.")
        callTimer?.cancel()
    }

    private fun startMissedCallTimer() {
        callTimer = Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    if (state == CallState.RINGING) {
                        println("Missed call.")
                        disconnectCall()
                    }
                }
            }, 30000) // 30 seconds
        }
    }

    private fun startActiveCallTimer() {
        callTimer = Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    disconnectCall()
                }
            }, 60000) // Example: disconnect after 1 minute
        }
    }
}